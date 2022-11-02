package ku.cs.service;

import ku.cs.model.CustomerList;

import java.io.*;

public class CustomerDataSource implements DataSource<CustomerList> {
    private String dirName;
    private String filename;

    // TODO: ทำตัวอ่านไฟล์
//    public  CustomerDataSource() {
//        this()
//    }

    @Override
    public CustomerList readData() {
        CustomerList customerList = new CustomerList();
        String path = dirName + File.separator + filename;
        File file = new File(path);

        FileReader fr = null;
        BufferedReader br = null;

        // TODO: ทำตัวอ่านไฟล์
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line = "";
            while ( ( line = br.readLine() ) != null) {
                String[] data = line.split(",");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return customerList;
    }

    @Override
    public void writeData(CustomerList customerList) {

    }
}
