package utility;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JTable;

// Code to generate a JTable from a ResultSet
public class TableFromResultSet {
	public static JTable convert(ResultSet rs, ResultSetMetaData rsmd){
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Vector<String> names = new Vector<String>();
		
		try{
			int numCols = rsmd.getColumnCount();
			for (int i = 0; i < numCols; i++)
			{ 
				names.add(rsmd.getColumnName(i+1));
			}
			
			while(rs.next())
			{
				Vector<String> d = new Vector<String>();
				for (int i = 0; i < numCols; i++)
				{ 
					d.add(rs.getString(rsmd.getColumnName(i+1)));
				}
				data.add(d);
			}
		}
		catch(SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}
		return new JTable(data,names);
	}
}
