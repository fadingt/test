package cn.com.agree.javatest;


import com.cn.ccc.ggg.encrypt.core.dao.HibernateEntityDao;
import com.cn.ccc.ggg.encrypt.core.dao.support.Page;
import com.cn.ccc.ggg.ldap.bean.entryInfo.PersonEntry;
import com.cn.ccc.ggg.ldap.core.common.LDIFReader;
import com.cn.ccc.ggg.ldap.core.novell.LDAPExport;
import com.cn.ccc.ggg.ldap.exception.LDAPException;
import com.cn.ccc.ggg.ldap.model.CertList;
import com.cn.ccc.ggg.ldap.model.LDAPConectionInfo;


import com.novell.ldap.*;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.io.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * 目录服务连接信息管理
 */
@Service("ldapConectionInfoService")
public class LDAPConnectionInfoService extends HibernateEntityDao<LDAPConectionInfo> {

    public static int DEFAULT_PAGE_SIZE = 500;

    private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

    private String msg = new String();

    private Integer d = new Integer(0);


    /**
     * @description:查询目录服务列表
     * @param: [condition, pageNo, pageSize]
     * @return: com.cn.ccc.ggg.encrypt.core.dao.support.Page
     * @exception:
     * @author: dq
     * @date: 13:39 2018/1/26
     */
    public Page findserverList(Map<String, Object> condition, int pageNo, int pageSize) throws LDAPException {
        ArrayList<Criterion> criteria = new ArrayList<Criterion>();
        if (condition.get("userId") != null) {
            Integer id = (Integer) condition.get("userId");
            criteria.add(Restrictions.eq("userId", id));
        }
        return pagedQuery(LDAPConectionInfo.class, pageNo, pageSize, "optTime", false, criteria.toArray(new Criterion[]{}));
    }

    public LDAPConectionInfo findServerInfoByUserIdAndServerName(int id, String serverName) {
        List<LDAPConectionInfo> list = createCriteria(LDAPConectionInfo.class, Restrictions.and(Restrictions.eq("userId", id), Restrictions.eq("serverName", serverName))).list();
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }


    /**
     * @description:连接并绑定目录服务系统，支持匿名访问，简单认证。不支持ssl连接。自动追加数据库名称
     * @param: [currinfo]   连接信息
     * @return: com.novell.ldap.LDAPConnection
     * @exception:
     * @author: dq
     * @date: 13:39 2018/1/26
     */
    public LDAPConnection connectionLDAP(LDAPConectionInfo currinfo) {
        LDAPConnection lc = new LDAPConnection();
        try {
            //连接目录服务
            lc.connect(currinfo.getIp(), currinfo.getPort());
            //绑定服务
            if (currinfo.getIsAnonymousBind() == 1) {     //匿名访问
                lc.bind(null, null);
            } else {                                      //简单认证
                String loginDN = currinfo.getUserDN();
                if (currinfo.getIsAppendBaseDN().equals(LDAPConectionInfo.ENABLE_STATUS))  //检查是否追加数据库名称
                    loginDN = currinfo.getUserDN() + "," + currinfo.getBaseDN();
                lc.bind(currinfo.getVersion(), loginDN, currinfo.getPassword().getBytes("UTF8"));
            }
        } catch (com.novell.ldap.LDAPException e) {
            d = 1;
            getErrorMsg(e);
            //连接失败
            return null;
        } catch (UnsupportedEncodingException e) {
            //转码异常
            msg = "编码异常";
        }
        return lc;
    }



    public Map<String,Object> getNumberOfEntries(LDAPConectionInfo info,String searchDN){
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        String searfiler = "(objectclass=*)";
        LDAPConnection lc = connectionLDAP(info);
        long total = 0L;
        d = 0;
        try {
            String attrs[] = {LDAPConnection.NO_ATTRS};
            LDAPSearchResults s = lc.search(searchDN, LDAPConnection.SCOPE_SUB,searfiler,attrs,true);
            while (s.hasMore()) {
                s.next();
                total++;
                //TODO 异常处理
            }

        } catch (com.novell.ldap.LDAPException e) {
            d = 1;
            getErrorMsg(e);
        }
        hashMap.put("status",d);
        hashMap.put("msg",msg);
        hashMap.put("total",total);
        return hashMap;
    }

    /**
     * @description:根据ip、port获取ldap的根节点
     * @param: [ip, port]
     * @return: java.util.List<java.util.Map < java.lang.String , java.lang.Object>>
     * @exception:
     * @author: dq
     * @date: 9:54 2018/2/26
     */
    public List<Map<String,Object>> getSuffix(String ip,int  port) {
        d = 0;msg = "";
        LDAPConnection lc = new LDAPConnection();
        List<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            lc.connect(ip,port);
        } catch (com.novell.ldap.LDAPException e) {
            d = 1;
            msg = "获取数据库名称失败，请检查ip和端口是否正确以及服务是否开启";
            logger.error(msg);
            return null;
        }
        String [] context = {"namingContexts"};
        try {
            LDAPSearchResults search = lc.search("", 0, "objectclass=*", context, false);
            while (search.hasMore()){
                LDAPEntry ldapEntry = search.next();
                LDAPAttributeSet attributeSet = ldapEntry.getAttributeSet();
                Iterator iterator = attributeSet.iterator();
                while (iterator.hasNext()){
                    LDAPAttribute next = (LDAPAttribute)iterator.next();
                    String nextName = next.getName();
                    if(nextName.equals("namingContexts")){
                        Enumeration stringValues = next.getStringValues();
                        while (stringValues.hasMoreElements()){
                            HashMap<String, Object> hashMap = new HashMap<String, Object>();
                            String dn = (String)stringValues.nextElement();
                            hashMap.put("DN",dn);
                            arrayList.add(hashMap);
                        }
                    }
                }
            }
        } catch (com.novell.ldap.LDAPException e) {
            e.printStackTrace();
            d = 1;
            msg = "获取数据库名称失败";
            return null;
        }
        return arrayList;

    }

    public Map<String,Object> importLDIF(LDAPConectionInfo info, File ldifFile){
        Integer errorSign = 0;
        Integer successSign = 0;
        LDIFReader reader = null;
        LDAPEntry entry;
        LDAPMessage msg, retMsg;
        LDAPConnection lc = new LDAPConnection();
        Map<String, Object> hashMap = new HashMap<String, Object>();
        try {
            FileInputStream fis = new FileInputStream(ldifFile);
            reader = new LDIFReader(fis, 1);
        } catch (Exception e) {
            logger.error("读取 " + ldifFile +"文件失败"+e.getMessage());
        }
        try {
            lc.connect( info.getIp(), info.getPort() );
            String loginDN = info.getUserDN();
            if(info.getIsAppendBaseDN().equals(LDAPConectionInfo.ENABLE_STATUS))  //检查是否追加数据库名称
                loginDN = info.getUserDN()+","+info.getBaseDN();
            lc.bind( info.getVersion(), loginDN, info.getPassword().getBytes("UTF8") );
            if (!reader.isRequest()) {
                while ( (msg = reader.readMessage()) != null) {
                    entry = ((LDAPSearchResult)msg).getEntry();
                }
            } else {
                while ( (msg = reader.readMessage()) != null) {
                    LDAPMessageQueue queue = lc.sendRequest(msg, null, null);
                    if ((retMsg = queue.getResponse()) != null) {
                        LDAPResponse response = (LDAPResponse)retMsg;
                        int status = response.getResultCode();
                        if ( status == com.novell.ldap.LDAPException.SUCCESS )
                            successSign++;
                        else  {
                            if(status == com.novell.ldap.LDAPException.ENTRY_ALREADY_EXISTS){
                                successSign++;
                            } else{
                                errorSign++;
                                if(response.getErrorMessage().length() != 0){
                                    logger.error("错误代码为:"+ status +response.getErrorMessage());
                                }

                            }

                        }
                    }
                }
            }
        } catch( UnsupportedEncodingException e ) {
            logger.error( "错误信息为:UnsupportedEncodingException");
        } catch ( IOException ioe ) {
            logger.error("错误信息为:IOException");
        } catch ( com.novell.ldap.LDAPException le ) {
            logger.error("错误信息为:LDAPException :"+le.getMessage());
        }
        hashMap.put("errorSign", errorSign);
        hashMap.put("successSign", successSign);
        return hashMap;
    }

   /**
    * @description:LDIF文件导出
    * @param: [info, baseDN, filePath]  连接信息；导出的根节点；导出的路径
    * @return: void
    * @exception:
    * @author: dq
    * @date: 13:37 2018/1/26
    */
    public void exportLDIF(LDAPConectionInfo info,String baseDN,String filePath){
        String loginDN = info.getUserDN();
        if(info.getIsAppendBaseDN().equals(LDAPConectionInfo.ENABLE_STATUS))  //检查是否追加数据库名称
            loginDN = info.getUserDN()+","+info.getBaseDN();
        String [] args = {info.getIp(),loginDN,info.getPassword(),baseDN,"objectClass=*",filePath};
        LDAPExport export = new LDAPExport();
        export.export(args);

    }

    /**
     * @description:重命名属性名称，若该属性不是子节点则失败
     * @param: [info, oldDN, newDN, parentDN]
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @exception:
     * @author: dq
     * @date: 13:32 2018/1/26
     */
    public  Map<String,Object> renameRDN(LDAPConectionInfo info,String oldDN,String newDN,String parentDN){
        d= 0;msg = "";
        Map<String, Object> map = new HashMap<String, Object>();
        LDAPConnection lc = connectionLDAP(info);
        if(null == lc){
            map.put("status",d);
            map.put("msg",msg);
            return map;
        }
        try {
            lc.rename(oldDN,newDN,parentDN,true);
            lc.disconnect();
        } catch (com.novell.ldap.LDAPException e) {
            d = 1;


        }
        map.put("status",d);
        map.put("msg",msg);
        return map;
    }
    /**
     * @description:修改指定条目的属性
     * @param: [info, searchDN, attrsVal]
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @exception:
     * @author: dq
     * @date: 13:50 2018/1/26
     */
    public Map<String,Object> modifyAttrs(LDAPConectionInfo info,String searchDN,List<Map<String,Object>> attrsVal){
        d= 0;msg = "";
        HashMap<String, Object> map = new HashMap<String, Object>();
        LDAPConnection lc = connectionLDAP(info);

        if(null == lc){
            map.put("status",d);
            map.put("msg",msg);
            return map;
        }
        String searchFilter = "(objectclass=*)";
        try {
            LDAPSearchResults search = lc.search(searchDN, LDAPConnection.SCOPE_BASE, searchFilter, null, false);
            while (search.hasMore()){
                LDAPEntry next = search.next();
                LDAPAttributeSet attributeSet = next.getAttributeSet();
                Iterator iterator = attributeSet.iterator();
                while (iterator.hasNext()){
                    LDAPAttribute attribute = (LDAPAttribute) iterator.next();
                    String attributeName = attribute.getName();
                    for (Map m: attrsVal){  //新的属性值
                        //动态修改条目属性值
                        if(m.containsKey(attributeName)) //检查key是否存在
                             lc.modify(searchDN,new LDAPModification(LDAPModification.ADD,new LDAPAttribute( attributeName, (String) m.get(attributeName))));
                    }
                }
            }

        } catch (com.novell.ldap.LDAPException e) {
            d =1;
            getErrorMsg(e);
        }
        map.put("status",d);
        map.put("msg",msg);
        return map;
    }

    /**
     * @description:添加条目（只支持用户和组织）
     * @param: [info, person, searchDN]
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @exception:
     * @author: dq
     * @date: 13:49 2018/1/26
     */
    public Map<String,Object> addEntry(LDAPConectionInfo info, PersonEntry person,String searchDN) {
        d= 0;msg = "";
        Map<String, Object> map = new HashMap<String,Object>();
        LDAPConnection lc = connectionLDAP(info);
        if(null == lc){
            map.put("status",d);
            map.put("msg",msg);
            return  map;
        }
        LDAPAttributeSet attributeSet = new LDAPAttributeSet();
        String [] att = {"top",person.getObjectclass()};
        attributeSet.add(new LDAPAttribute("objectclass",att));
        if(person.getSn().length() > 0)
            attributeSet.add(new LDAPAttribute("sn",person.getSn()));
        if(person.getUserPassword().length() > 0 )
            attributeSet.add(new LDAPAttribute("userpassword",person.getUserPassword()));
        if( person.getTelephoneNumber().length() > 0)
            attributeSet.add(new LDAPAttribute("telephoneNumber",person.getTelephoneNumber()));
        if( person.getDescription().length() > 0)
            attributeSet.add(new LDAPAttribute("description",person.getDescription()));
        if( person.getSeeAlso().length() > 0  )
            attributeSet.add(new LDAPAttribute("seeAlso",person.getSeeAlso()));

        String dn = person.getDn() + "," + searchDN;
        LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);
        try {
            lc.add(newEntry);
            lc.disconnect();
        } catch (com.novell.ldap.LDAPException e) {
            d= 1;
            getErrorMsg(e);
        }
        map.put("status",d);
        map.put("msg",msg);
        return map;

    }

    /**
     * @description:删除指定的节点，若该节点存在子节点将一并删除
     * @param: [info, base, baseDN]
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @exception:
     * @author: dq
     * @date: 13:47 2018/1/26
     */
    public Map<String, Object> delete(LDAPConectionInfo info,int base, String baseDN){
        d= 0;msg = "";
        Map<String, Object> map = new HashMap<String, Object>();
        LDAPConnection lc = connectionLDAP(info);
        if(null == lc){
            map.put("status",d);
            map.put("msg",msg);
            return  map;
        }
        String searchFilter = "(objectclass=*)";
        try {
            LDAPSearchResults search = lc.search(baseDN, base, searchFilter, null, false);
            while (search.hasMore()){
                String dn = search.next().getDN();
                delete(info,LDAPConnection.SCOPE_ONE,dn);
                lc.delete(dn);
            }
        } catch (com.novell.ldap.LDAPException e) {
            d = 1;
            getErrorMsg(e);
        }
        map.put("msg",msg);
        map.put("status",d);

        return map;
    }

    /**
     * @description:返回子节点的条目。
     * @param: [currInfo, searchBase, searchFilter, scope, currentPageNo]
     * @return: java.util.List<java.util.Map < java.lang.String , java.lang.Object>>
     * @exception:
     * @author: dq
     * @date: 13:46 2018/1/26
     */
    public List<Map<String, Object>> search(LDAPConectionInfo currInfo, String searchBase, String searchFilter, int scope,int currentPageNo) {
        d= 0;msg = "";
        StringBuffer str = new StringBuffer();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        LDAPConnection lc = connectionLDAP(currInfo);
        if(null == lc){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("status",d);
            map.put("msg",msg);
            mapList.add(map);
            return  mapList;
        }
        long l = 0L;

        try {
            String attrs[] = {LDAPConnection.NO_ATTRS};
            LDAPSearchResults searchResults = lc.search(searchBase,
                    scope, // 搜索的类型，遍历、子节点、 LDAPConnection.SCOPE_BASE base 0:自身，one 1:子节点，sub 2:所有
                    searchFilter,
                    attrs,          // “1.1”只返回条目名称
                    true);// 不返回属性和属性值
            long totalCount = (long)currentPageNo * pageSize ;
            long current = (long)( currentPageNo - 1 ) * pageSize + 1;
            while (searchResults.hasMore()) {   //遍历所有条目
                Map<String, Object> map = new HashMap<String, Object>();
                LDAPEntry nextEntry = null;
                try {
                    nextEntry = searchResults.next();
                } catch (com.novell.ldap.LDAPException e) {
                    d = 1;
                    HashMap<String, Object> m = new HashMap<String, Object>();
                    getErrorMsg(e);
                    m.put("status",d);
                    m.put("msg",msg);
                    mapList.add(m);

                    // 抛出异常，进入下一个条目
                    if (e.getResultCode() == com.novell.ldap.LDAPException.LDAP_TIMEOUT || e.getResultCode() == com.novell.ldap.LDAPException.CONNECT_ERROR)
                        break;
                    else
                        continue;
                }
                l++;
                 if(l >= current && l <= totalCount){     //查询需要的条目数是介于current条至totalCount条
                    map.put("isParent", true);
                    int a = currentPageNo != 1 ? currentPageNo : 1;
                    map.put("pageNo",a);
                    String dn = nextEntry.getDN();
                    map.put("baseDN", dn);   //保存DN
                     long total = getTotal(searchFilter, lc, dn);
                     map.put("totalRecord",total);
                    if (scope != 0) dn = dn.substring(0, dn.indexOf(","));
                    map.put("text", dn);    //去除父节点名称，用于前端显示
                     mapList.add(map);
                }
            }
            lc.disconnect();
        } catch (com.novell.ldap.LDAPException e) {
            //getErrorMsg(e);
        }
        return mapList;
    }

    /**
     * @description:获取节点自身的属性和属性值
     * @param: [currInfo, searchBase, searchFilter]
     * @return: java.util.List<java.util.Map < java.lang.String , java.lang.Object>>
     * @exception:
     * @author: dq
     * @date: 13:45 2018/1/26
     */
    public List<Map<String, Object>> attAndValue(LDAPConectionInfo currInfo, String searchBase, String searchFilter) {
        d= 0;msg = "";
        StringBuffer str = new StringBuffer();
        LDAPConnection lc = connectionLDAP(currInfo);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if(null == lc){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("status",d);
            map.put("msg",msg);
            mapList.add(map);
            return  mapList;
        }

        try {
            LDAPSearchResults searchResults = lc.search(searchBase,
                    LDAPConnection.SCOPE_BASE, // 搜索的类型，遍历、子节点、 LDAPConnection.SCOPE_BASE base 0:自身，one 1:子节点，sub 2:所有
                    searchFilter,
                    null,          // return all attributes
                    false);// return attrs and values

            while (searchResults.hasMore()) {   //遍历所有条目
                Map<String, Object> map = new HashMap<String, Object>();
                LDAPEntry nextEntry = null;
                try {
                    nextEntry = searchResults.next();
                } catch (com.novell.ldap.LDAPException e) {
                    System.out.println("Error: " + e.toString());
                    // Exception is thrown, go for next entry
                    if (e.getResultCode() == com.novell.ldap.LDAPException.LDAP_TIMEOUT || e.getResultCode() == com.novell.ldap.LDAPException.CONNECT_ERROR)
                        break;
                    else
                        continue;
                }
                map.put("baseDN", nextEntry.getDN());   //保存DN
                LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
                Iterator allAttributes = attributeSet.iterator();
                HashMap<String, Object> hashMap = new HashMap<String, Object>();

                while (allAttributes.hasNext()) { //遍历所有属性
                    LDAPAttribute attribute = (LDAPAttribute) allAttributes.next();
                    String attributeName = attribute.getName();
                    byte[] byteValue = attribute.getByteValue();
                    String s = new String(byteValue);
                    Enumeration byteValues = attribute.getByteValues();
                    if(byteValues != null){
                        while (byteValues.hasMoreElements()){
                            Object oneVal = byteValues.nextElement();
                            if(attributeName .endsWith("binary")){
                                try {
                                    StringBuffer buffer = readCer(attributeName, oneVal);
                                    hashMap.put(attributeName,buffer.toString());
                                } catch (CertificateException e) {
                                    System.out.println(e.getMessage());
                                }
                            }else  if(oneVal instanceof  String ){
                                hashMap.put(attributeName ,(String) oneVal);
                            } else if(oneVal instanceof  byte []){
                                try {
                                    hashMap.put(attributeName ,new String ((byte[] )oneVal,"UTF-8"));
                                } catch (UnsupportedEncodingException e) {
                                    //TODO
                                }
                            }
                        }
                    }
                }
                map.put("attributes",hashMap);
                mapList.add(map);
                }
            lc.disconnect();
        } catch (com.novell.ldap.LDAPException e) {
            e.printStackTrace();
        }
        return mapList;
    }


    /**
     * @description:统计该节点下的所有数据量
     * @param: [searchFilter, lc, nextEntry]
     * @return: long
     * @exception:
     * @author: dq
     * @date: 13:44 2018/1/26
     */
    public long getTotal(String searchFilter, LDAPConnection lc, String dn) throws com.novell.ldap.LDAPException {
        String attrs[] = {LDAPConnection.NO_ATTRS};
        LDAPSearchResults s = lc.search(dn, LDAPConnection.SCOPE_ONE,searchFilter,attrs,true);
        long total = 0L;
        while (s.hasMore()) {
            s.next();
            total++;
            //TODO 异常处理
        }
        return total;
    }

    /**
     * @description:解析证书
     * @param: [attributeName, oneVal]
     * @return: java.lang.StringBuffer 返回StringBuffer类型的字符串
     * @exception:
     * @author: dq
     * @date: 13:43 2018/1/26
     */
    public  StringBuffer readCer( String attributeName, Object oneVal) throws CertificateException{
        byte[] byteCert = (byte[]) oneVal;
        //转换成二进制流
        ByteArrayInputStream bain = new ByteArrayInputStream(byteCert);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate oCert = null;
        StringBuffer buffer = new StringBuffer();
        try {
            oCert = (X509Certificate) cf.generateCertificate(bain);
            if (null != oCert) {
                String serianNum = oCert.getSerialNumber().toString();    //序列号
                String issuerDn = oCert.getIssuerDN().getName();        //发布方标识名
                String subDN = oCert.getSubjectDN().getName();            //主体标识
                String sigAlgOID = oCert.getSigAlgOID();                //证书算法OID字符串
                String noAfter = oCert.getNotAfter().toGMTString();        //证书有效期
                String sigAlg = oCert.getSigAlgName().toString();        //签名算法
                int version = oCert.getVersion();                        //版本号
                String publicKey = oCert.getPublicKey().getFormat();    //公钥

                buffer.append("版本号: " + version);
                buffer.append("; 序列号: " + serianNum);
                buffer.append("; 签名算法: " + sigAlg);
                buffer.append("; 签发者: " + issuerDn);
                buffer.append("; 有效期: " + noAfter);
                buffer.append("; 使用者: " + subDN);
                buffer.append("; 算法OID: " + sigAlgOID);
                buffer.append("; 公钥: " + publicKey);
            } else {
                organizeCer(buffer);
            }
        } catch (Exception e) {
            //无法解析或者解析失败
            if(attributeName.equals("userCertificate;binary")){
                organizeCer(buffer);
            }else{
                buffer.append("binary");
            }
        }
        return  buffer;
    }

    /**
     * @description:若解析证书失败则显示N/A
     * @param: [buffer]
     * @return: void
     * @exception:
     * @author: dq
     * @date: 13:42 2018/1/26
     */
    public void organizeCer(StringBuffer buffer) {
        buffer.append("版本号: N/A");
        buffer.append("; 序列号: N/A");
        buffer.append("; 签名算法: N/A");
        buffer.append("; 签发者: N/A");
        buffer.append("; 有效期: N/A");
        buffer.append("; 使用者: N/A");
        buffer.append("; 算法OID: N/A");
        buffer.append("; 公钥: N/A");
    }

    /**
     * @description:将List<Map<K,V>>对象转为String类型
     * @param: [search]
     * @return: java.lang.String
     * @exception:
     * @author: dq
     * @date: 13:41 2018/1/26
     */
    public static String getJsonByListMap(List<Map<String, Object>> search) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONArray json = JSONArray.fromObject(search, jsonConfig);
        return json.toString();
    }
    /**
     * @description:将Map对象转为String类型
     * @param: [search]
     * @return: java.lang.String
     * @exception:
     * @author: dq
     * @date: 13:40 2018/1/26
     */
    public static String getJsonByMap(Map<String, Object> search) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONArray json = JSONArray.fromObject(search, jsonConfig);
        return json.toString();
    }

    private void getErrorMsg(com.novell.ldap.LDAPException e) {
        if(e.getResultCode() == com.novell.ldap.LDAPException.OPERATIONS_ERROR) {//1
            msg = "操作错误";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.PROTOCOL_ERROR){//2
            msg = "服务器收到来自客户端的无效或格式错误的请求";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.TIME_LIMIT_EXCEEDED){//3
            msg = "已超出客户端或服务器指定的操作时间限制";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.SIZE_LIMIT_EXCEEDED){//4
            msg = "超出了客户端或服务器指定的大小限制";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.AUTH_METHOD_NOT_SUPPORTED){//7
            msg = "绑定操作期间，客户端请求LDAP服务器时，采用了不支持的身份验证方法";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.STRONG_AUTH_REQUIRED){//8
            msg = "客户端请求了需要强认证的操作，如删除操作";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.LDAP_PARTIAL_RESULTS){//9
            msg = "LDAP部分结果";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.REFERRAL){//10
            msg = "REFERRAL";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.ADMIN_LIMIT_EXCEEDED){//11
            msg = "已超出由管理权限设置的LDAP服务器限制";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.UNAVAILABLE_CRITICAL_EXTENSION){//12
            msg = "服务器不支持该控件或该控件不适合该操作类型";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.CONFIDENTIALITY_REQUIRED){//13
            msg = "会话不受诸如传输层安全性（TLS）之类的提供会话机密性的协议的保护";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.SASL_BIND_IN_PROGRESS){//14
            msg = "需要SASL绑定";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.NO_SUCH_ATTRIBUTE){//16
            msg = "指定的属性在条目中不存在";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.UNDEFINED_ATTRIBUTE_TYPE){//17
            msg = "指定的属性在LDAP服务器的模式中不存在";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.INAPPROPRIATE_MATCHING){//18
            msg = "搜索过滤器中指定的匹配规则与为该属性的语法定义的规则不匹配";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.CONSTRAINT_VIOLATION){//19
            msg = "指定的属性值违反了放置在属性上的约束。约束可以是大小或内容之一（例如，仅字符串，不是二进制数据）";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.ATTRIBUTE_OR_VALUE_EXISTS){//20
            msg = "指定的属性值已经作为该属性的值存在";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.INVALID_ATTRIBUTE_SYNTAX){//21
            msg = "无效的属性语法";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.NO_SUCH_OBJECT){//32
            msg = "无法找到目标对象";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.ALIAS_PROBLEM){//33
            msg = "取消别名时发生错误";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.INVALID_DN_SYNTAX){//34
            msg = "DN的语法不正确";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.ALIAS_DEREFERENCING_PROBLEM){//36
            msg = "无权读取别名对象的名称，或者不允许取消引用";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.INAPPROPRIATE_AUTHENTICATION){//48
            msg = "无法正确使用的身份验证方法";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.INVALID_CREDENTIALS){//49
            msg = "无效的凭证，请检查用户和密码是否正确";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.INSUFFICIENT_ACCESS_RIGHTS){//50
            msg = "访问权限不够";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.BUSY){//51
            msg = "无法处理客户端请求，但重新提交请求，服务器可能会处理该请求";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.UNAVAILABLE){//52
            msg = "正在关闭中，无法处理绑定请求";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.UNWILLING_TO_PERFORM){//53
            msg = "请求违反了服务器的结构规则，定义的限制，无法处理该请求";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.LOOP_DETECT){//54
            msg = "发现别名或引用循环，因此无法完成此请求";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.NAMING_VIOLATION){//64
            msg = "违反了模式的结构规则";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.OBJECT_CLASS_VIOLATION){//65
            msg = "违反条目的对象类规则";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.NOT_ALLOWED_ON_NONLEAF){//66
            msg = "不允许在非叶结点执行此操作";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.NOT_ALLOWED_ON_RDN){//67
            msg = "不允许对RDN执行此操作";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.ENTRY_ALREADY_EXISTS){//68
            msg = "条目已存在";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.OBJECT_CLASS_MODS_PROHIBITED){//69
            msg = "禁止更改对象类的结构规则";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.OTHER){//80
            msg = "未知的错误情况";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.LOCAL_ERROR){//82
            msg = "本地错误";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.ENCODING_ERROR){//83
            msg = "编码错误";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.DECODING_ERROR){//84
            msg = "解码错误";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.LDAP_TIMEOUT){//85
            msg = "等待结果时超出LDAP客户端的时间限制";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.AUTH_UNKNOWN){//86
            msg = "未知的身份验证方法调用绑定方法";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.FILTER_ERROR){//87
            msg = "使用无效的搜索过滤器调用搜索方法";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.USER_CANCELLED){//88
            msg = "用户取消了LDAP操作";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.NO_MEMORY){//90
            msg = "调用LDAP方法时动态内存分配方法失败";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.CONNECT_ERROR){//91
            msg = "连接失败，请检查配置信息是否正确以及服务是否开启";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.LDAP_NOT_SUPPORTED){//92
            msg = "请求的功能不支持";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.CONTROL_NOT_FOUND){//93
            msg = "控制未发现";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.NO_RESULTS_RETURNED){//94
            msg = "没有返回结果";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.MORE_RESULTS_TO_RETURN){//95
            msg = "更多的结果返回";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.CLIENT_LOOP){//96
            msg = "客户端循环";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.REFERRAL_LIMIT_EXCEEDED){//97
            msg = "超过限制";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.INVALID_RESPONSE){//100
            msg = "无效的响应";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.AMBIGUOUS_RESPONSE){//101
            msg = "请求的响应不明确";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.TLS_NOT_SUPPORTED){//112
            msg = "不支持TLS";
        } else {
            msg = "操作失败，错误代码："+e.getResultCode()+" "+e.getMessage();
        }

    }


}