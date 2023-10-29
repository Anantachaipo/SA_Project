package ku.cs.service;

import ku.cs.model.LawyerList;
import ku.cs.model.Lawyer;
import ku.cs.model.UserList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LawyerListFileDataSource implements DataSourceLawyer<LawyerList>{
    private String directoryName;
    private String filename;

    public LawyerListFileDataSource(){
        this("file_csv","lawyer.csv");
    }

    public LawyerListFileDataSource(String directoryName, String filename){
        this.directoryName = directoryName;
        this.filename = filename;
        initialFileIfNotExist();
    }
    //ถ้าไฟล์หายจะสร้างใหม่
    private void initialFileIfNotExist(){
        File file = new File(directoryName);
        if (!file.exists()){
            file.mkdir();
        }

        String path = directoryName + File.separator + filename;
        file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public LawyerList readDataLawyer() {
        LawyerList lawyerList = new LawyerList();
        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileReader reader = null;
        BufferedReader buffer = null;
        try{
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);

            String line = "";
            while( (line = buffer.readLine()) != null){
                String [] data = line.split(",");
                String role = data[0];
                if(role.equals("Lawyer")){
                    lawyerList.addLawyer(new Lawyer(data[1],
                            data[2],
                            data[3],
                            data[4],
                            data[3],
                            data[6],
                            data[7],
                            data[8],
                            data[9],
                            data[10],
                            data[11],
                            data[12],
                            data[13],
                            data[14]));
                }
//                else if(role.equals("Admin")){
//                    userList.addUser(new Admin(data_csv.text[1],
//                            data_csv.text[2],
//                            data_csv.text[3],
//                            data_csv.text[4],
//                            LocalDateTime.parse(data_csv.text[5])));
//                }
            }
            buffer.close();
            reader.close();

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                buffer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lawyerList;
    }

    @Override
    public void writeDataLawyer(LawyerList lawyerList) {
        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try{
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            buffer.write(lawyerList.toCsv());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
