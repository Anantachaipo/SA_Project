package ku.cs.service;

import ku.cs.model.LawsuitsInformation;
import ku.cs.model.LawsuitsInformationList;
import ku.cs.model.Lawyer;
import ku.cs.model.LawyerList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LawsuitsInformationListFileDataSource implements DataSourceLawsuitsInformation<LawsuitsInformationList> {
    private String directoryName;
    private String filename;

    public LawsuitsInformationListFileDataSource(){
        this("file_csv","lawsuitsInformation.csv");
    }

    public LawsuitsInformationListFileDataSource(String directoryName, String filename){
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
    public LawsuitsInformationList readDataLawsuitsInformation() {
        LawsuitsInformationList lawsuitsInformationList = new LawsuitsInformationList();
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
                if(role.equals("LawsuitsInformation")){
                    LawsuitsInformationList.addLawsuitsInformation(new LawsuitsInformation(data[1],
                            data[2],
                            data[3],
                            data[4]));
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
        return lawsuitsInformationList;
    }

    @Override
    public void writeDataLawsuitsInformation(LawsuitsInformationList lawsuitsInformationList) {
        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try{
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            buffer.write(lawsuitsInformationList.toCsv());
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
