package cn.com.agree.openldap;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Iterator;

import com.novell.ldap.*;
import com.novell.ldap.util.Base64;
import com.novell.ldap.util.LDAPWriter;


/**
 * <p>Title: 向LDIF文件中写入内容</p>
 * <p>Description: 生成LDIF文件并写入内容</p>
 * @author Administrator
 * @date 2017年11月10日 上午10:51:04
 * 
 */
public class LDIFWriter implements LDAPWriter
{

    private Boolean         requestFile = null;         
    private BufferedWriter  bufWriter;
    private String          version;

    
    /**
     * 假定LDIF版本为1
     * @param out
     * @throws IOException
     */
    public LDIFWriter(OutputStream out)throws IOException{
        this( out, "1", null );
        return;
    }

   
    public LDIFWriter(OutputStream out, String version, boolean request) throws IOException{
        this( out, version, new Boolean(request));
        return;
    }

    
    private LDIFWriter(OutputStream out, String version, Boolean request) throws IOException {
        super();

        if ( version != "1" ) 
            throw new RuntimeException( "com.novell.ldap.ldif_dsml.LDIFWriter: LDIF version:found: " + version + ", Should be: 1");

        this.version = version;
        requestFile = request;
        OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
        bufWriter = new BufferedWriter( osw );
        //writeComments("这个头部注释信息");
        //writeVersionLine();
        return;
    }

    
    public void writeEntry(LDAPEntry entry) throws IOException {
            writeEntry(entry, new LDAPControl[]{});
            return;
    }

    /**
     * Write an LDAP record into LDIF file as LDAPContent data.
     *
     * <p>You are not allowed to mix request data and content data</p>
     *
     * @param entry LDAPEntry object
     *
     * @param controls Controls that were returned with this entry
     *
     * @throws IOException if an I/O error occurs.
     *
     * @see com.novell.ldap.LDAPEntry
     */
    public void writeEntry(LDAPEntry entry, LDAPControl[] controls)
                throws IOException
    {
            requestFile = Boolean.FALSE; // This is a content file
            writeAddRequest(entry, controls);
            bufWriter.newLine();
            return;
    }

    /**
     * Write an LDAP record into LDIF file. A request or change operation may
     * be objects of type  LDAPAddRequest, LDAPDeleteRequest,
     * LDAPModifyDNRequest, or LDAPModifyRequest.
     * To write LDIF Content you must use an LDAPSearchResult object.
     *
     * <p>You are not allowed to mix request data and content data</p>
     *
     * @param request LDAPMessage object
     *
     * @throws IOException if an I/O error occurs.
     *
     * @see com.novell.ldap.LDAPSearchResults
     * @see com.novell.ldap.LDAPAddRequest
     * @see com.novell.ldap.LDAPDeleteRequest
     * @see com.novell.ldap.LDAPModifyDNRequest
     * @see com.novell.ldap.LDAPModifyRequest
     */
    public void writeMessage(LDAPMessage request)
                throws IOException
    {
        LDAPControl[]  controls = request.getControls();

        // check for valid type
        switch( request.getType()) {
        case LDAPMessage.SEARCH_RESPONSE:
            if( requestFile == null) {
                requestFile = Boolean.FALSE;
            }
            if( isRequest()) {
                throw new RuntimeException("Attempting to write content " +
                        " in a request stream");
            }
            break;
        case LDAPMessage.ADD_REQUEST:
        case LDAPMessage.DEL_REQUEST:
        case LDAPMessage.MODIFY_RDN_REQUEST:
        case LDAPMessage.MODIFY_REQUEST:
            if( requestFile == null) {
                requestFile = Boolean.TRUE;
            }
            if( ! isRequest()) {
                throw new RuntimeException("Attempting to write request " +
                        " in a content stream");
            }
            break;
        default:
            throw new RuntimeException("Unsupported request type: " +
                    request.toString());
        }

        switch( request.getType()) {
        case LDAPMessage.SEARCH_RESPONSE:
            // LDAP Search Result Entry, write entry to outputStream
            LDAPSearchResult sreq = (LDAPSearchResult)request;
            writeAddRequest(sreq.getEntry(), controls);
            break;
        case LDAPMessage.ADD_REQUEST:
            // LDAPAdd request, write entry to outputStream
            LDAPAddRequest areq = (LDAPAddRequest)request;
            writeAddRequest(areq.getEntry(), controls);
            break;
        case LDAPMessage.DEL_REQUEST:
            // LDAPDelete request, write dn to outputStream
            LDAPDeleteRequest dreq = (LDAPDeleteRequest)request;
            writeDeleteRequest( dreq.getDN(), controls );
            break;
        case LDAPMessage.MODIFY_RDN_REQUEST:
            // LDAPModDN request, write request data to outputStream
            LDAPModifyDNRequest rreq = (LDAPModifyDNRequest)request;
            // write to outputStream
            writeModifyDNRequest( rreq.getDN(),
                                  rreq.getNewRDN(),
                                  rreq.getDeleteOldRDN(),
                                  rreq.getParentDN(),
                                  controls );
            break;
        case LDAPMessage.MODIFY_REQUEST:
            // LDAPModify request, write modifications to outputStream
            LDAPModifyRequest mreq = (LDAPModifyRequest)request;
            // write to outputStream
            writeModifyRequest( mreq.getDN(), mreq.getModifications(), controls );
            break;
        }
        // write an empty line to separate records
        bufWriter.newLine();
        return;
    }

    /**
     * Write a comment line into the LDIF OutputStream.
     *
     * <p> an '#' char is added to the front of each line to indicate that
     * the line is a comment line. If a line contains more than 78
     * chars, it will be split into multiple lines each of which starts
     * with '#' </p>
     *
     * @param line The comment lines to be written to the OutputStream
     *
     * @throws IOException if an I/O error occurs.
     */
    public void writeComments (String line) throws IOException
    {
        BufferedReader in = new BufferedReader(new StringReader( line));
        String rline;
        while( (rline = in.readLine()) != null) {
            bufWriter.write("# ", 0, 2);
            bufWriter.write(rline, 0, rline.length());
            bufWriter.newLine();
        }
        return;
    }

    /**
     * Writes an exception as a comment in LDIF.
     * @param e  Exception to be written.
     */
    public void writeError(Exception e) throws IOException {
        this.writeComments(e.toString());
    }

    /**
     * Gets the version of the LDIF data associated with the input stream
     *
     * @return the version number
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * Returns true if request data ist associated with the input stream,
     * or false if content data.
     *
     * @return true if input stream contains request data.
     */
    public boolean isRequest()
    {
        return requestFile.booleanValue();
    }

    /**
     * Check if the input byte array object is safe to make a String.
     *
     * <p>Check if the input byte array contains any un-printable value</p>
     *
     * @param bytes The byte array object to be checked.
     *
     * @return boolean object to incidate that the byte array
     * object is safe or not
     */
    public boolean isPrintable( byte[] bytes )
    {
        if (bytes == null) {
            throw new RuntimeException(
                    "com.novell.ldap.ldif_dsml.LDIFWriter: null pointer");
        }
        else if (bytes.length > 0) {
            for (int i=0; i<bytes.length; i++) {
                if ( (bytes[i]&0x00ff) < 0x20 || (bytes[i]&0x00ff) > 0x7e ) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Write the version line of LDIF file into the OutputStream.
     *
     * <p>Two extra lines will be written to separate version line
     * with the rest of lines in LDIF file</p>
     *
     * @throws IOException if an I/O error occurs.
     */
    private void writeVersionLine () throws IOException
    {
        // LDIF file is currently using 'version 1'
        String versionLine = new String("version: " + getVersion());
        bufWriter.write( versionLine, 0, versionLine.length());
        // write an empty line to separate the version line
        // with the rest of the contents in LDIF file
        bufWriter.newLine();
        bufWriter.newLine();
        return;
    }

    /**
     * Write a line into the OutputStream.
     *
     * <p>If the line contains more than 80 chars, it will be splited into
     * multiple lines each of which starts with a space ( ASCII ' ') except
     * the first one.</p>
     *
     * @param line The line to be written to the OutputStream
     *
     * @throws IOException if an I/O error occurs.
     */
    private void writeLine(String line) throws IOException
    {
        int len = line.length();
        if ( (line != null) && (len != 0)) {
            if (len <= 76) {
                // write short line
                bufWriter.write(line, 0, len);
            } else {
                int pos = 0;
                // break up long line
                // write the first 80 chars to outputStream (no blank at front)
                bufWriter.write(line, pos, 76);
                pos += 76;
                // remove the chars that already been written out
                bufWriter.newLine();

                while((len - pos) > 75) {
                    // write continuation line
                    bufWriter.write(" ", 0, 1);
                    bufWriter.write(line, pos, 75);
                    // remove the chars that already been written out
                    pos += 75;
                    // start a new line
                    bufWriter.newLine();
                }
                // write the remaining part of the lien out
                bufWriter.write(" ", 0, 1);
                bufWriter.write(line, pos, len - pos);
            }
            // write an empty line
            bufWriter.newLine();
        }
    }

    /**
     * Used to generate LDIF content record or LDIF change/add record lines.
     *
     * <p>Turn LDAPEntry object and LDAPControl[] object into LDIF record
     * lines</p>
     *
     * @param entry  LDAPREntry object
     * @param ctrls  LDAPControl object
     */
    private void writeAddRequest( LDAPEntry entry, LDAPControl[] ctrls )
            throws IOException
    {

        // write dn line(s)
        writeDN(entry.getDN());
        if (isRequest()) {
            // add control line(s)
            if ( ctrls != null ) {
                writeControls( ctrls );
            }
            // add change type line
            writeLine("changetype: add");
        }

        // write attribute line(s)
        LDAPAttributeSet attrSet = entry.getAttributeSet();
        Iterator allAttrs = attrSet.iterator();

        while(allAttrs.hasNext()) {
            LDAPAttribute attr = (LDAPAttribute)allAttrs.next();
            String attrName = attr.getName();
            byte[][] values = attr.getByteValueArray();

            if( values != null) {
               for (int i=0; i<values.length; i++) {
                   writeAttribute(attrName, values[i]);
               }
            }
        }
        return;
    }

    /**
     * Used to generate LDIF change/modify record lines.
     *
     * <p>Turn entry DN, LDAPModification[] object, and LDAPControl[] object
     * into LDIF LDIF record fields and then turn record fields into LDIF
     * change/modify record lines</p>
     *
     * @param dn    String object representing entry DN
     * @param mods  LDAPModification array object
     * @param ctrls LDAPControl array object
     *
     * @see LDAPModification
     * @see LDAPControl
     */
    private void writeModifyRequest( String dn,
                                     LDAPModification[] mods,
                                     LDAPControl[] ctrls )
                throws IOException
    {

        int i, modOp, len = mods.length;
        String attrName;
        byte[][] attrValue;
        LDAPAttribute attr;

        // Write the dn field
        writeDN(dn);
        // write controls if there is any
        if ( ctrls != null ) {
            writeControls( ctrls );
        }

        // save change type
        writeLine("changetype: modify");

        // save attribute names and values
        for ( i = 0; i < len; i++ ) {

            modOp = mods[i].getOp();
            attr =  mods[i].getAttribute();
            attrName = attr.getName();
            attrValue = attr.getByteValueArray();

            switch ( modOp )  {
                case LDAPModification.ADD:
                    writeLine("add: "+ attrName);
                    break;
                case LDAPModification.DELETE:
                    writeLine("delete: "+ attrName);
                    break;
                case LDAPModification.REPLACE:
                    writeLine("replace: "+ attrName);
                    break;
                default:
            }

            // add attribute names and values to record fields
            for(int j = 0;j < attrValue.length;j++)
                writeAttribute(attrName, attrValue[j]);


            // add separators between different modify operations
            writeLine("-");
        }
        return;
    }

    /**
     * Used to generate LDIF change/moddn record lines.
     *
     * <p>Turn entry DN and moddn information into LDIF change/modify
     * record lines</p>
     *
     * @param dn      String object representing entry DN
     * @param newRDN  The NewRDN for the ModDN request
     * @param deleteOldRDN the deleteOldRDN flag
     * @param newSuperior   the new Superior DN for a move, or null if rename
     * @param ctrls   LDAPControl array object
     */
    private void writeModifyDNRequest( String dn,
                                       String newRDN,
                                       boolean deleteOldRDN,
                                       String newSuperior,
                                       LDAPControl[] ctrls )
                  throws IOException
    {
        // Write the dn field
        writeDN(dn);

        // write controls if there is any
        if ( ctrls != null ) {
            writeControls( ctrls );
        }

        // save change type
        writeLine("changetype: moddn");

        // save new RDN
        writeLine( Base64.isLDIFSafe(newRDN)?
            "newrdn: "  + newRDN:
            "newrdn:: " + Base64.encode(newRDN));

        // save deleteOldRDN
        writeLine("deleteoldrdn:" + deleteOldRDN);

        // save newSuperior
        if ( newSuperior != null) {
            writeLine( Base64.isLDIFSafe(newSuperior)?
                "newsuperior: "  + newSuperior:
                "newsuperior:: " +  Base64.encode(newSuperior));
        }
        return;
    }

    /**
     * Used to generate LDIF change/delete record lines.
     *
     * <p>Turn entry DN, controls
     * and change type into LDIF change/delete record fields and then turn
     * record fields into LDIF moddn record lines</p>
     *
     * @param dn    String object representing entry DN
     * @param ctrls LDAPControl array object
     *
     * @see LDAPControl
     */
    private void writeDeleteRequest( String dn,
                                   LDAPControl[] ctrls )
                throws IOException
    {
        // write dn line(s)
        writeDN(dn);
        // write control line(s)
        if ( ctrls != null ) {
            writeControls( ctrls );
        }
        // write change type
        writeLine("changetype: delete");
        return;
    }

    /**
     * Write the DN to the outputStream.  If the DN characters are unsafe,
     * the DN is encoded.
     *
     * @param dn the DN to write
     */
    private void writeDN(String dn)
                throws IOException
    {
        writeLine(Base64.isLDIFSafe(dn)? "dn: "+dn: "dn:: "+ Base64.encode(dn));
        return;
    }

    /**
     * Write control line(s).
     *
     * @param ctrls LDAPControl array object
     */
    private void writeControls(LDAPControl[] ctrls)
                throws IOException
    {
        for ( int i = 0; i < ctrls.length; i++ ) {
            // get control value
            byte[] cVal = ctrls[i].getValue();
            // write control value
            writeLine( (cVal != null && cVal.length > 0)?
                "control: " + ctrls[i].getID() + " " + ctrls[i].isCritical()
                            + ":: " + Base64.encode(cVal):
                "control: " + ctrls[i].getID() + " " + ctrls[i].isCritical() );
        }
        return;
    }

    /**
     * Write attribute name and value into outputStream.
     *
     * <p>Check if attrVal starts with NUL, LF, CR, ' ', ':', or '<'
     * or contains any NUL, LF, or CR, and then write it out</p>
     */
    private void writeAttribute(String attrName, String attrVal)
                throws IOException
    {
        if (attrVal != null) {
            writeLine( Base64.isLDIFSafe(attrVal)?
                attrName + ": "  + attrVal :
                attrName + ":: " + Base64.encode(attrVal) );
        }
        return;
    }

    /**
     * Write attribute name and value into outputStream.
     *
     * <p>Check if attribute value contains NON-SAFE-INIT-CHAR or
     * NON-SAFE-CHAR. if it does, it needs to be base64 encoded and then
     * write it out</p>
     */
    private void writeAttribute(String attrName, byte[] attrVal)
                throws IOException
    {
        if (attrVal != null) {
            writeLine( (Base64.isLDIFSafe(attrVal) && isPrintable(attrVal))?
                attrName + ": " + new String(attrVal, "UTF-8"):
                attrName + ":: " + Base64.encode(attrVal) );
        }
        return;
    }

    /**
     * Write all remaining data to the output stream
     */
    public void finish() throws IOException
    {
        bufWriter.flush();
        return;
    }
}