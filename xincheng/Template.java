package org.java;

public enum Template {
	
	/**
	 * 模板路径
	 */
	TEMPLATEPATH("E:\\xincheng\\hibcool-1.1.0\\batch\\"),
	
	/**
	 * 模板生成路径
	 */
	GENERATEPATH("E:\\xincheng\\hibcool-1.1.0\\luoyy\\"),

	/**
	 * columns模板
	 */
	COLUMNS("package #PackageName#;  import java.io.Serializable; import kyle.common.dbaccess.query.GeneralColumns;  public class #ColumnName# extends GeneralColumns implements Serializable {  private static final long serialVersionUID = -8265284628323765505L;  public #ColumnName#() { m_astrColumns = new String[#ColumnSize#]; }  public #ColumnName#(#FieldParam#){ m_astrColumns = new String[#ColumnSize#]; #SetFun# }  #GetSet#  public String toString() { return \"Code Generate By Kyle\"; }	  } "),
	
	/**
	 * condition模板
	 */
	CONDITION("package #PackageName#;  import kyle.common.dbaccess.query.GeneralCondition;  public class #ConditionName# extends GeneralCondition { public #ConditionName#() { m_astrConditions = new String[#ConditionSize#]; }	  #GetSet#  } "),
	
	/**
	 * query模板
	 */
	QUERY("package #PackageName#;  import kyle.common.dbaccess.query.IColumns; import kyle.common.dbaccess.query.JGeneralQuery;  public class #QueryName#Query extends JGeneralQuery {  public #QueryName#Query(){ m_strSelectClause = \"select #Select# from #From#\"; m_strWhereClause = \"#Where#\"; m_strOrderByClause = \"#Orderby#\"; m_strGroupByClause = \"#Groupby#\"; m_astrConditionWords = new String[] { #ConditionWords# }; m_aiConditionVariableCount = new int[] { #ConditionCount# };		 }  @Override public IColumns createColumns() { return new #QueryName#Columns(); }  #GetSet#  } ");
	
	private final String content; 
    private Template(String content) { 
           this.content = content; 
    } 
    
    public String getContent() { 
        return content; 
    } 
}
