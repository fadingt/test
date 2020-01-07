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
 * Ŀ¼����������Ϣ����
 */
@Service("ldapConectionInfoService")
public class LDAPConnectionInfoService extends HibernateEntityDao<LDAPConectionInfo> {

    public static int DEFAULT_PAGE_SIZE = 500;

    private int pageSize = DEFAULT_PAGE_SIZE; // ÿҳ�ļ�¼��

    private String msg = new String();

    private Integer d = new Integer(0);


    /**
     * @description:��ѯĿ¼�����б�
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
     * @description:���Ӳ���Ŀ¼����ϵͳ��֧���������ʣ�����֤����֧��ssl���ӡ��Զ�׷�����ݿ�����
     * @param: [currinfo]   ������Ϣ
     * @return: com.novell.ldap.LDAPConnection
     * @exception:
     * @author: dq
     * @date: 13:39 2018/1/26
     */
    public LDAPConnection connectionLDAP(LDAPConectionInfo currinfo) {
        LDAPConnection lc = new LDAPConnection();
        try {
            //����Ŀ¼����
            lc.connect(currinfo.getIp(), currinfo.getPort());
            //�󶨷���
            if (currinfo.getIsAnonymousBind() == 1) {     //��������
                lc.bind(null, null);
            } else {                                      //����֤
                String loginDN = currinfo.getUserDN();
                if (currinfo.getIsAppendBaseDN().equals(LDAPConectionInfo.ENABLE_STATUS))  //����Ƿ�׷�����ݿ�����
                    loginDN = currinfo.getUserDN() + "," + currinfo.getBaseDN();
                lc.bind(currinfo.getVersion(), loginDN, currinfo.getPassword().getBytes("UTF8"));
            }
        } catch (com.novell.ldap.LDAPException e) {
            d = 1;
            getErrorMsg(e);
            //����ʧ��
            return null;
        } catch (UnsupportedEncodingException e) {
            //ת���쳣
            msg = "�����쳣";
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
                //TODO �쳣����
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
     * @description:����ip��port��ȡldap�ĸ��ڵ�
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
            msg = "��ȡ���ݿ�����ʧ�ܣ�����ip�Ͷ˿��Ƿ���ȷ�Լ������Ƿ���";
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
            msg = "��ȡ���ݿ�����ʧ��";
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
            logger.error("��ȡ " + ldifFile +"�ļ�ʧ��"+e.getMessage());
        }
        try {
            lc.connect( info.getIp(), info.getPort() );
            String loginDN = info.getUserDN();
            if(info.getIsAppendBaseDN().equals(LDAPConectionInfo.ENABLE_STATUS))  //����Ƿ�׷�����ݿ�����
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
                                    logger.error("�������Ϊ:"+ status +response.getErrorMessage());
                                }

                            }

                        }
                    }
                }
            }
        } catch( UnsupportedEncodingException e ) {
            logger.error( "������ϢΪ:UnsupportedEncodingException");
        } catch ( IOException ioe ) {
            logger.error("������ϢΪ:IOException");
        } catch ( com.novell.ldap.LDAPException le ) {
            logger.error("������ϢΪ:LDAPException :"+le.getMessage());
        }
        hashMap.put("errorSign", errorSign);
        hashMap.put("successSign", successSign);
        return hashMap;
    }

   /**
    * @description:LDIF�ļ�����
    * @param: [info, baseDN, filePath]  ������Ϣ�������ĸ��ڵ㣻������·��
    * @return: void
    * @exception:
    * @author: dq
    * @date: 13:37 2018/1/26
    */
    public void exportLDIF(LDAPConectionInfo info,String baseDN,String filePath){
        String loginDN = info.getUserDN();
        if(info.getIsAppendBaseDN().equals(LDAPConectionInfo.ENABLE_STATUS))  //����Ƿ�׷�����ݿ�����
            loginDN = info.getUserDN()+","+info.getBaseDN();
        String [] args = {info.getIp(),loginDN,info.getPassword(),baseDN,"objectClass=*",filePath};
        LDAPExport export = new LDAPExport();
        export.export(args);

    }

    /**
     * @description:�������������ƣ��������Բ����ӽڵ���ʧ��
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
     * @description:�޸�ָ����Ŀ������
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
                    for (Map m: attrsVal){  //�µ�����ֵ
                        //��̬�޸���Ŀ����ֵ
                        if(m.containsKey(attributeName)) //���key�Ƿ����
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
     * @description:�����Ŀ��ֻ֧���û�����֯��
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
     * @description:ɾ��ָ���Ľڵ㣬���ýڵ�����ӽڵ㽫һ��ɾ��
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
     * @description:�����ӽڵ����Ŀ��
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
                    scope, // ���������ͣ��������ӽڵ㡢 LDAPConnection.SCOPE_BASE base 0:����one 1:�ӽڵ㣬sub 2:����
                    searchFilter,
                    attrs,          // ��1.1��ֻ������Ŀ����
                    true);// ���������Ժ�����ֵ
            long totalCount = (long)currentPageNo * pageSize ;
            long current = (long)( currentPageNo - 1 ) * pageSize + 1;
            while (searchResults.hasMore()) {   //����������Ŀ
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

                    // �׳��쳣��������һ����Ŀ
                    if (e.getResultCode() == com.novell.ldap.LDAPException.LDAP_TIMEOUT || e.getResultCode() == com.novell.ldap.LDAPException.CONNECT_ERROR)
                        break;
                    else
                        continue;
                }
                l++;
                 if(l >= current && l <= totalCount){     //��ѯ��Ҫ����Ŀ���ǽ���current����totalCount��
                    map.put("isParent", true);
                    int a = currentPageNo != 1 ? currentPageNo : 1;
                    map.put("pageNo",a);
                    String dn = nextEntry.getDN();
                    map.put("baseDN", dn);   //����DN
                     long total = getTotal(searchFilter, lc, dn);
                     map.put("totalRecord",total);
                    if (scope != 0) dn = dn.substring(0, dn.indexOf(","));
                    map.put("text", dn);    //ȥ�����ڵ����ƣ�����ǰ����ʾ
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
     * @description:��ȡ�ڵ���������Ժ�����ֵ
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
                    LDAPConnection.SCOPE_BASE, // ���������ͣ��������ӽڵ㡢 LDAPConnection.SCOPE_BASE base 0:����one 1:�ӽڵ㣬sub 2:����
                    searchFilter,
                    null,          // return all attributes
                    false);// return attrs and values

            while (searchResults.hasMore()) {   //����������Ŀ
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
                map.put("baseDN", nextEntry.getDN());   //����DN
                LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
                Iterator allAttributes = attributeSet.iterator();
                HashMap<String, Object> hashMap = new HashMap<String, Object>();

                while (allAttributes.hasNext()) { //������������
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
     * @description:ͳ�Ƹýڵ��µ�����������
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
            //TODO �쳣����
        }
        return total;
    }

    /**
     * @description:����֤��
     * @param: [attributeName, oneVal]
     * @return: java.lang.StringBuffer ����StringBuffer���͵��ַ���
     * @exception:
     * @author: dq
     * @date: 13:43 2018/1/26
     */
    public  StringBuffer readCer( String attributeName, Object oneVal) throws CertificateException{
        byte[] byteCert = (byte[]) oneVal;
        //ת���ɶ�������
        ByteArrayInputStream bain = new ByteArrayInputStream(byteCert);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate oCert = null;
        StringBuffer buffer = new StringBuffer();
        try {
            oCert = (X509Certificate) cf.generateCertificate(bain);
            if (null != oCert) {
                String serianNum = oCert.getSerialNumber().toString();    //���к�
                String issuerDn = oCert.getIssuerDN().getName();        //��������ʶ��
                String subDN = oCert.getSubjectDN().getName();            //�����ʶ
                String sigAlgOID = oCert.getSigAlgOID();                //֤���㷨OID�ַ���
                String noAfter = oCert.getNotAfter().toGMTString();        //֤����Ч��
                String sigAlg = oCert.getSigAlgName().toString();        //ǩ���㷨
                int version = oCert.getVersion();                        //�汾��
                String publicKey = oCert.getPublicKey().getFormat();    //��Կ

                buffer.append("�汾��: " + version);
                buffer.append("; ���к�: " + serianNum);
                buffer.append("; ǩ���㷨: " + sigAlg);
                buffer.append("; ǩ����: " + issuerDn);
                buffer.append("; ��Ч��: " + noAfter);
                buffer.append("; ʹ����: " + subDN);
                buffer.append("; �㷨OID: " + sigAlgOID);
                buffer.append("; ��Կ: " + publicKey);
            } else {
                organizeCer(buffer);
            }
        } catch (Exception e) {
            //�޷��������߽���ʧ��
            if(attributeName.equals("userCertificate;binary")){
                organizeCer(buffer);
            }else{
                buffer.append("binary");
            }
        }
        return  buffer;
    }

    /**
     * @description:������֤��ʧ������ʾN/A
     * @param: [buffer]
     * @return: void
     * @exception:
     * @author: dq
     * @date: 13:42 2018/1/26
     */
    public void organizeCer(StringBuffer buffer) {
        buffer.append("�汾��: N/A");
        buffer.append("; ���к�: N/A");
        buffer.append("; ǩ���㷨: N/A");
        buffer.append("; ǩ����: N/A");
        buffer.append("; ��Ч��: N/A");
        buffer.append("; ʹ����: N/A");
        buffer.append("; �㷨OID: N/A");
        buffer.append("; ��Կ: N/A");
    }

    /**
     * @description:��List<Map<K,V>>����תΪString����
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
     * @description:��Map����תΪString����
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
            msg = "��������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.PROTOCOL_ERROR){//2
            msg = "�������յ����Կͻ��˵���Ч���ʽ���������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.TIME_LIMIT_EXCEEDED){//3
            msg = "�ѳ����ͻ��˻������ָ���Ĳ���ʱ������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.SIZE_LIMIT_EXCEEDED){//4
            msg = "�����˿ͻ��˻������ָ���Ĵ�С����";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.AUTH_METHOD_NOT_SUPPORTED){//7
            msg = "�󶨲����ڼ䣬�ͻ�������LDAP������ʱ�������˲�֧�ֵ������֤����";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.STRONG_AUTH_REQUIRED){//8
            msg = "�ͻ�����������Ҫǿ��֤�Ĳ�������ɾ������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.LDAP_PARTIAL_RESULTS){//9
            msg = "LDAP���ֽ��";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.REFERRAL){//10
            msg = "REFERRAL";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.ADMIN_LIMIT_EXCEEDED){//11
            msg = "�ѳ����ɹ���Ȩ�����õ�LDAP����������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.UNAVAILABLE_CRITICAL_EXTENSION){//12
            msg = "��������֧�ָÿؼ���ÿؼ����ʺϸò�������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.CONFIDENTIALITY_REQUIRED){//13
            msg = "�Ự�������紫��㰲ȫ�ԣ�TLS��֮����ṩ�Ự�����Ե�Э��ı���";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.SASL_BIND_IN_PROGRESS){//14
            msg = "��ҪSASL��";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.NO_SUCH_ATTRIBUTE){//16
            msg = "ָ������������Ŀ�в�����";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.UNDEFINED_ATTRIBUTE_TYPE){//17
            msg = "ָ����������LDAP��������ģʽ�в�����";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.INAPPROPRIATE_MATCHING){//18
            msg = "������������ָ����ƥ�������Ϊ�����Ե��﷨����Ĺ���ƥ��";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.CONSTRAINT_VIOLATION){//19
            msg = "ָ��������ֵΥ���˷����������ϵ�Լ����Լ�������Ǵ�С������֮һ�����磬���ַ��������Ƕ��������ݣ�";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.ATTRIBUTE_OR_VALUE_EXISTS){//20
            msg = "ָ��������ֵ�Ѿ���Ϊ�����Ե�ֵ����";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.INVALID_ATTRIBUTE_SYNTAX){//21
            msg = "��Ч�������﷨";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.NO_SUCH_OBJECT){//32
            msg = "�޷��ҵ�Ŀ�����";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.ALIAS_PROBLEM){//33
            msg = "ȡ������ʱ��������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.INVALID_DN_SYNTAX){//34
            msg = "DN���﷨����ȷ";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.ALIAS_DEREFERENCING_PROBLEM){//36
            msg = "��Ȩ��ȡ������������ƣ����߲�����ȡ������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.INAPPROPRIATE_AUTHENTICATION){//48
            msg = "�޷���ȷʹ�õ������֤����";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.INVALID_CREDENTIALS){//49
            msg = "��Ч��ƾ֤�������û��������Ƿ���ȷ";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.INSUFFICIENT_ACCESS_RIGHTS){//50
            msg = "����Ȩ�޲���";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.BUSY){//51
            msg = "�޷�����ͻ������󣬵������ύ���󣬷��������ܻᴦ�������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.UNAVAILABLE){//52
            msg = "���ڹر��У��޷����������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.UNWILLING_TO_PERFORM){//53
            msg = "����Υ���˷������Ľṹ���򣬶�������ƣ��޷����������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.LOOP_DETECT){//54
            msg = "���ֱ���������ѭ��������޷���ɴ�����";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.NAMING_VIOLATION){//64
            msg = "Υ����ģʽ�Ľṹ����";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.OBJECT_CLASS_VIOLATION){//65
            msg = "Υ����Ŀ�Ķ��������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.NOT_ALLOWED_ON_NONLEAF){//66
            msg = "�������ڷ�Ҷ���ִ�д˲���";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.NOT_ALLOWED_ON_RDN){//67
            msg = "�������RDNִ�д˲���";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.ENTRY_ALREADY_EXISTS){//68
            msg = "��Ŀ�Ѵ���";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.OBJECT_CLASS_MODS_PROHIBITED){//69
            msg = "��ֹ���Ķ�����Ľṹ����";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.OTHER){//80
            msg = "δ֪�Ĵ������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.LOCAL_ERROR){//82
            msg = "���ش���";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.ENCODING_ERROR){//83
            msg = "�������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.DECODING_ERROR){//84
            msg = "�������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.LDAP_TIMEOUT){//85
            msg = "�ȴ����ʱ����LDAP�ͻ��˵�ʱ������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.AUTH_UNKNOWN){//86
            msg = "δ֪�������֤�������ð󶨷���";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.FILTER_ERROR){//87
            msg = "ʹ����Ч������������������������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.USER_CANCELLED){//88
            msg = "�û�ȡ����LDAP����";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.NO_MEMORY){//90
            msg = "����LDAP����ʱ��̬�ڴ���䷽��ʧ��";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.CONNECT_ERROR){//91
            msg = "����ʧ�ܣ�����������Ϣ�Ƿ���ȷ�Լ������Ƿ���";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.LDAP_NOT_SUPPORTED){//92
            msg = "����Ĺ��ܲ�֧��";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.CONTROL_NOT_FOUND){//93
            msg = "����δ����";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.NO_RESULTS_RETURNED){//94
            msg = "û�з��ؽ��";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.MORE_RESULTS_TO_RETURN){//95
            msg = "����Ľ������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.CLIENT_LOOP){//96
            msg = "�ͻ���ѭ��";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.REFERRAL_LIMIT_EXCEEDED){//97
            msg = "��������";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.INVALID_RESPONSE){//100
            msg = "��Ч����Ӧ";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.AMBIGUOUS_RESPONSE){//101
            msg = "�������Ӧ����ȷ";
        }else if(e.getResultCode() == com.novell.ldap.LDAPException.TLS_NOT_SUPPORTED){//112
            msg = "��֧��TLS";
        } else {
            msg = "����ʧ�ܣ�������룺"+e.getResultCode()+" "+e.getMessage();
        }

    }


}