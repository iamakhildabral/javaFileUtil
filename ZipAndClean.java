import java.io.*;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipAndClean {
    public static void main(String[] args) throws IOException {
        String directoryPath = "C:\\Users\\AKHIL\\Pictures\\Screenshots";
        String extension = "png";
        List<String> fileNames = listFileWithExtension(directoryPath, extension);
        System.out.println("Total Count of Files are :"+fileNames.size());
        for (String name :
                fileNames) {
            System.out.println(name);
        }

        //creating zip file name
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.add(Calendar.MONTH,-1);
        String monthName = new DateFormatSymbols().getMonths()[cal.get(Calendar.MONTH)];
        String zipFileName =monthName+"zip";

        createZipFiles(directoryPath,zipFileName,fileNames);


    }

    public static List<String> listFileWithExtension(String directoryPath, String extension){
        List<String> filesToBeExecuted = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.isDirectory()){

            // making list of all the files to be zipped and deleted
            File[] fileList = directory.listFiles();

            // checking for extension
            assert fileList != null;
            for (File file : fileList) {
                if (file.isFile() && file.getName().endsWith(extension)){
                    filesToBeExecuted.add(file.getName());
                }
            }
        }else {
            System.err.println("Error :"+directoryPath+" is not a directory");
        }
        return filesToBeExecuted;
    }

    public static void createZipFiles(String directoryPath,String zipFileName,List<String> fileNames) throws IOException {
        FileOutputStream fos = new FileOutputStream(directoryPath+"/"+zipFileName);
        ZipOutputStream zos = new ZipOutputStream(fos);
        try {
            for (String name :
                    fileNames) {
                File file = new File(directoryPath + "/" + name);
                FileInputStream fis = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(name);
                zos.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;

                while ((length = fis.read(bytes)) >= 0){
                    zos.write(bytes,0,length);
                }
                fis.close();

            }

            System.out.println("Zip file created at "+directoryPath+" with name "+zipFileName);

            deleteExtensionFile(directoryPath,fileNames);





            // calling delete here just to make sure zip happened successfully

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            fos.close();
            zos.close();
        }
    }

    public static void deleteExtensionFile(String directoryPath,List<String> fileNames){
        System.out.println("Total Count of Files are :"+fileNames.size());
        for (String name :
                fileNames) {
            System.out.println(name);
        }

        System.out.println("File Delete Start after Zip");
        for (String name : fileNames){
            File file = new File(directoryPath+"/"+name);
            if (file.delete()){
                System.out.println("File Deleted : "+file.getName());
            }else {
                System.out.println("Failed to delete File : "+file.getName());
            }
        }
    }
}
