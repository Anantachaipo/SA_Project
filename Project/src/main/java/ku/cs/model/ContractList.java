package ku.cs.model;

import java.util.ArrayList;

public class ContractList {

    private ArrayList<Contract> contracts;

    public ContractList() {
        contracts = new ArrayList<>();
    }

    public void addContract(Contract contract) {
        contracts.add(contract);
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    public Contract searchByID(int id) {
        for (Contract contract : contracts) {
            if (contract.getC_Id() == id) {
                return contract;
            }
        }
        return null;
    }
}
