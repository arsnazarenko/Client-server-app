package gui.graphicsInterface.mainWindow.table;

import javax.swing.table.AbstractTableModel;
import java.util.*;

public class TableModel extends AbstractTableModel{
    private  ArrayList<Object[]> dataList;

    public TableModel() {
        dataList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            dataList.add(new Object[getColumnCount()]);
        }
    }

    @Override
    public int getRowCount() {
        return dataList.size();
    }

    @Override
    public int getColumnCount() {
        return 15;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex!=-1){
            Object[] o = dataList.get(rowIndex);
            return o[columnIndex];
        }
        return null;
    }

    public ArrayList<Object[]> getDataList() {
        return dataList;

    }

    public void setDataList(ArrayList<Object[]> data) {
        this.dataList = data;
        fireTableDataChanged();
    }

}
