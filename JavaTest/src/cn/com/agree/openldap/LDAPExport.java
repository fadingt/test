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
 * <p>Title: ����LDIF�ļ�</p>
 * <p>Description: ����LDAPĿ¼��������������Ŀ��д�뵽LDIF�ļ���</p>
 * @author Administrator
 * @date 2017��11��10�� ����8:49:47
 * 
 */
public class LDAPExport{

    public static void main(String[] args) {
        
        String[] arg = {"192.168.0.254","cn=Manager,c=cn","123456","c=cn","objectClass=*","D:/dqcer.ldif"};
        LDAPExport ldapExport = new LDAPExport();
        ldapExport.export(arg);
    }
    public  void export( String[] args ) {
        //�������
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

            lc.connect( ldapHost, ldapPort );    // ���ӷ���
            lc.bind(ldapVersion,loginDN,password.getBytes("UTF8") );    // �󶨷���
            FileOutputStream fos = new FileOutputStream(fileName);
            
            LDIFWriter writer = new LDIFWriter(fos);    //��ʼ��

           //�첽����
            LDAPSearchQueue queue = lc.search(searchBase,        // �����Ľڵ�
                                    LDAPConnection.SCOPE_SUB,    // ���������ͣ��������ӽڵ㡢
                                    searchFilter,               // ��������������
                                    null,                          // ����ȫ��������
                                    false,                      // �������Ժ�����ֵ
                                    (LDAPSearchQueue)null,        // �ж�����
                                    (LDAPSearchConstraints)null);

            while (( msg = queue.getResponse()) != null ) {

               // ������Ϣ���ɹ��ο�
                if ( msg instanceof LDAPSearchResultReference ) {
                    String urls[] = ((LDAPSearchResultReference)msg).getReferrals();
                    System.out.println("Search result references:");
                        for ( int i = 0; i < urls.length; i++ )
                            System.out.println(urls[i]);
                // ���������Ľ��
                }else if (msg instanceof LDAPSearchResult ) {    
                    writer.writeMessage(msg);    // д��
               // ��������������
                } else {    
                    LDAPResponse response = (LDAPResponse)msg;
                    int status = response.getResultCode();    //����״̬��
                
                    // ���سɹ� 0
                    if ( status == LDAPException.SUCCESS ) {
                        System.out.println("Asynchronous search succeeded.");
                    // �쳣����
                    } else if ( status == LDAPException.REFERRAL ) {    
                        String urls[]=((LDAPResponse)msg).getReferrals();
                        System.out.println("Referrals:");
                        for ( int i = 0; i < urls.length; i++ )
                            System.out.println(urls[i]);
                    // �첽����ʧ��
                    } else {
                        throw new LDAPException( response.getErrorMessage(),status,response.getMatchedDN());
                    }
                }
            }

           // �ر������
            writer.finish();
            fos.close();
            System.out.println("����ldif�ļ�");
           // ��������Ͽ�����
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

        System.err.println("�÷�:   java Ldap2Ldif <host name> <login dn>"

                + " <password> <search base> <search filter> < out file name>");

        System.err.println("ʾ��:   192.168.0.254"

                                    + " \"cn=Manager,c=cn\""

                                    + " 123456 \"c=cn\"\n"

                                    + "         \"objectclass=*\" out.ldif");

        System.exit(1);

    }

}