package cn.com.agree.openldap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPMessage;
import com.novell.ldap.LDAPResponse;
import com.novell.ldap.LDAPSearchConstraints;
import com.novell.ldap.LDAPSearchQueue;
import com.novell.ldap.LDAPSearchResult;
import com.novell.ldap.LDAPSearchResultReference;


/**
 * <p>Title: 导出LDIF文件</p>
 * <p>Description: 搜索LDAP目录，并将搜索的条目数写入到LDIF文件中</p>
 * @author Administrator
 * @date 2017年11月10日 上午8:49:47
 * 
 */
public class LDAPExport{

    public static void main(String[] args) {
        
        String[] arg = {"192.168.0.254","cn=Manager,c=cn","123456","c=cn","objectClass=*","D:/dqcer.ldif"};
        LDAPExport ldapExport = new LDAPExport();
        ldapExport.export(arg);
    }
    public  void export( String[] args ) {
        //参数检查
        if (args.length != 6) usage();
        
        int ldapPort = LDAPConnection.DEFAULT_PORT;
        int ldapVersion = LDAPConnection.LDAP_V3;
        LDAPConnection lc = new LDAPConnection();
        String ldapHost = args[0];
        String loginDN = args[1];
        String password = args[2];
        String searchBase = args[3];
        String searchFilter= args[4];
        String fileName = args[5];
        LDAPMessage msg;

        try {

            lc.connect( ldapHost, ldapPort );    // 连接服务
            lc.bind(ldapVersion,loginDN,password.getBytes("UTF8") );    // 绑定服务
            FileOutputStream fos = new FileOutputStream(fileName);
            
            LDIFWriter writer = new LDIFWriter(fos);    //初始化

           //异步搜索
            LDAPSearchQueue queue = lc.search(searchBase,        // 搜索的节点
                                    LDAPConnection.SCOPE_SUB,    // 搜索的类型，遍历、子节点、
                                    searchFilter,               // 过滤搜索的条件
                                    null,                          // 搜索全部的属性
                                    false,                      // 搜索属性和属性值
                                    (LDAPSearchQueue)null,        // 列队搜索
                                    (LDAPSearchConstraints)null);

            while (( msg = queue.getResponse()) != null ) {

               // 返回消息，可供参考
                if ( msg instanceof LDAPSearchResultReference ) {
                    String urls[] = ((LDAPSearchResultReference)msg).getReferrals();
                    System.out.println("Search result references:");
                        for ( int i = 0; i < urls.length; i++ )
                            System.out.println(urls[i]);
                // 属于搜索的结果
                }else if (msg instanceof LDAPSearchResult ) {    
                    writer.writeMessage(msg);    // 写入
               // 属于搜索的请求
                } else {    
                    LDAPResponse response = (LDAPResponse)msg;
                    int status = response.getResultCode();    //返回状态码
                
                    // 返回成功 0
                    if ( status == LDAPException.SUCCESS ) {
                        System.out.println("Asynchronous search succeeded.");
                    // 异常参照
                    } else if ( status == LDAPException.REFERRAL ) {    
                        String urls[]=((LDAPResponse)msg).getReferrals();
                        System.out.println("Referrals:");
                        for ( int i = 0; i < urls.length; i++ )
                            System.out.println(urls[i]);
                    // 异步搜索失败
                    } else {
                        throw new LDAPException( response.getErrorMessage(),status,response.getMatchedDN());
                    }
                }
            }

           // 关闭输出流
            writer.finish();
            fos.close();
            System.out.println("生成ldif文件");
           // 与服务器断开连接
            lc.disconnect();
        }catch( UnsupportedEncodingException e ) {
            System.out.println( "Error: " + e.toString() );

        } catch (IOException fe) {
            System.out.println( "Error: " + fe.toString() );

        } catch( LDAPException e ) {
            System.out.println( "Error: " + e.toString() );

        }

    }



    public  void usage() {

        System.err.println("用法:   java Ldap2Ldif <host name> <login dn>"

                + " <password> <search base> <search filter> < out file name>");

        System.err.println("示例:   192.168.0.254"

                                    + " \"cn=Manager,c=cn\""

                                    + " 123456 \"c=cn\"\n"

                                    + "         \"objectclass=*\" out.ldif");

        System.exit(1);

    }

}