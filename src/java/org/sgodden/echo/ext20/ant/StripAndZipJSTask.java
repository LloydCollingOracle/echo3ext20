package org.sgodden.echo.ext20.ant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import nextapp.echo.webcontainer.util.GZipCompressor;
import nextapp.echo.webcontainer.util.JavaScriptCompressor;
import nextapp.echo.webcontainer.util.Resource;
import nextapp.echo.webcontainer.util.Resource.ResourceException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.FileSet;

public class StripAndZipJSTask extends MatchingTask {
    
    private static final int BUFFER_SIZE = 4096;
    
    List<FileSet> files = new ArrayList<FileSet>();
    String destFile;

    public void addFileset(FileSet set) {
        files.add(set);
    }
    
    public void setDestFile(String fileName) {
        this.destFile = fileName;
    }

    
    @Override
    public void execute() throws BuildException {
        StringBuffer out = new StringBuffer();
        for (FileSet fs : files) {
            DirectoryScanner rs = fs.getDirectoryScanner(getProject());
            String[] filesS = rs.getIncludedFiles();
            
            for (int i = 0; i < filesS.length; ++i) {
                out.append(getResource(new File(rs.getBasedir(), filesS[i])));
                out.append("\n\n");
            }
        }
        String compressed = JavaScriptCompressor.compress(out.toString());
        
        File outputFile = new File(getProject().getBaseDir(), destFile);
        outputFile.getParentFile().mkdirs();
        PrintWriter pw;
        try {
            pw = new PrintWriter(outputFile);
            pw.print(compressed);
            pw.close();
        } catch (FileNotFoundException e) {
            throw new BuildException("Error writing to output file", e);
        }
        
        byte[] gzipped;
        try {
            gzipped = GZipCompressor.compress(compressed);
            outputFile = new File(outputFile.toString() + ".gz");
            FileOutputStream outStream = new FileOutputStream(outputFile);
            outStream.write(gzipped);
            outStream.close();
        } catch (IOException e) {
            throw new BuildException("Error writing to gzip output file", e);
        }
    }
    

    
    /**
     * An internal method used to retrieve a resource as a
     * <code>ByteArrayOutputStream</code>.
     *
     * @param resourceName The name of the resource to be retrieved.
     * @return A <code>ByteArrayOutputStream</code> of the content of the
     *         resource.
     */
    private static ByteArrayOutputStream getResource(File resource) {
        InputStream in = null;
        byte[] buffer = new byte[BUFFER_SIZE];
        ByteArrayOutputStream out = null;
        int bytesRead = 0;
        
        try {
            in = new FileInputStream(resource);
            out = new ByteArrayOutputStream();
            do {
                bytesRead = in.read(buffer);
                if (bytesRead > 0) {
                    out.write(buffer, 0, bytesRead);
                }
            } while (bytesRead > 0);
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot get resource: \"" + resource.toString() + "\".", ex);
        } finally {
            if (in != null) { try { in.close(); } catch (IOException ex) { } } 
        }
        
        return out;
    }
}
