package com.ibm.bi.dml.hops;

import com.ibm.bi.dml.lops.Lops;
import com.ibm.bi.dml.lops.PartitionLop;
import com.ibm.bi.dml.lops.LopProperties.ExecType;
import com.ibm.bi.dml.meta.PartitionParams;
import com.ibm.bi.dml.parser.Expression.DataType;
import com.ibm.bi.dml.parser.Expression.ValueType;
import com.ibm.bi.dml.sql.sqllops.SQLLops;
import com.ibm.bi.dml.utils.HopsException;
import com.ibm.bi.dml.utils.LopsException;


public class PartitionOp extends Hops {
	PartitionParams pp ;
	
	private PartitionOp() {
		//default constructor for clone
	}
	
	public PartitionOp(String l, DataType dt, ValueType vt, PartitionParams pp, DataOp input) {
		super(Hops.Kind.PartitionOp, l, dt, vt);
		getInput().add(input) ;
		input.getParent().add(this) ;
		
		this.pp = pp ;
	}

	@Override
	public String getOpString() {
		return pp.toString();
	}
	
	public void printMe() throws HopsException {
		if (LOG.isDebugEnabled()){
			if (get_visited() != VISIT_STATUS.DONE) {
				super.printMe();
				LOG.debug("  Partition: " + pp.toString());
				for (Hops h : getInput()) {
					h.printMe();
				}
			}
			set_visited(VISIT_STATUS.DONE);
		}
	}

	@Override
	public Lops constructLops() throws HopsException, LopsException {
		if(get_lops() == null) {
			Lops dataLop = getInput().get(0).constructLops();
			PartitionLop pLop = new PartitionLop(pp, dataLop, get_dataType(), get_valueType()) ;
			pLop.getOutputParameters().setDimensions(getInput().get(0).get_dim1(),
					getInput().get(0).get_dim2(),  
					getInput().get(0).get_rows_in_block(), 
					getInput().get(0).get_cols_in_block(),
					getInput().get(0).getNnz()) ;
			pLop.setAllPositions(this.getBeginLine(), this.getBeginColumn(), this.getEndLine(), this.getEndColumn());
			set_lops(pLop) ;
		}
		return get_lops() ;
	}

	@Override
	public SQLLops constructSQLLOPs() throws HopsException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected double computeOutputMemEstimate( long dim1, long dim2, long nnz )
	{		
		return OptimizerUtils.INVALID_SIZE;
	}
	
	@Override
	protected double computeIntermediateMemEstimate( long dim1, long dim2, long nnz )
	{
		return OptimizerUtils.INVALID_SIZE;
	}
	
	@Override
	protected long[] inferOutputCharacteristics( MemoTable memo )
	{
		return null;
	}
	
	@Override
	public boolean allowsAllExecTypes()
	{
		return false;
	}
	
	@Override
	protected ExecType optFindExecType() throws HopsException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void refreshSizeInformation()
	{
		// TODO modify whenever CL/EL integrated into the optimizer
	}
		
	@Override
	public Object clone() throws CloneNotSupportedException 
	{
		PartitionOp ret = new PartitionOp();	
		
		//copy generic attributes
		ret.clone(this, false);
		
		//copy specific attributes
		// TODO modify whenever CL/EL integrated into the optimizer
		
		return ret;
	}
	
	@Override
	public boolean compare( Hops that )
	{
		return false;
	}
}
