package com.ibm.bi.dml.hops;

import com.ibm.bi.dml.lops.CrossvalLop;
import com.ibm.bi.dml.lops.Lops;
import com.ibm.bi.dml.lops.LopProperties.ExecType;
import com.ibm.bi.dml.parser.Expression.DataType;
import com.ibm.bi.dml.parser.Expression.ValueType;
import com.ibm.bi.dml.sql.sqllops.SQLLops;
import com.ibm.bi.dml.utils.HopsException;
import com.ibm.bi.dml.utils.LopsException;


public class CrossvalOp extends Hops {

	private CrossvalOp() {
		//default constructor for clone
	}
	
	public CrossvalOp(String l, DataType dt, ValueType vt, PartitionOp input) {
		super(Hops.Kind.CrossvalOp, l, dt, vt);
		getInput().add(input) ;
		input.getParent().add(this) ;
	}

	@Override
	public String getOpString() {
		String s = new String("");
		s += "CV";
		return s;
	}

	public void printMe() throws HopsException {
		if (LOG.isDebugEnabled()){
			if (get_visited() != VISIT_STATUS.DONE) {
				super.printMe();
				LOG.debug("  CrossvalOp: ");
				for (Hops h : getInput()) {
					h.printMe();
				}
			}
			set_visited(VISIT_STATUS.DONE);
		}
	}

	public Lops constructLops() throws HopsException, LopsException {
		if(get_lops() == null) {
			Lops pLop = getInput().get(0).constructLops() ;
			CrossvalLop cvlop = new CrossvalLop(pLop) ;
			cvlop.setAllPositions(this.getBeginLine(), this.getBeginColumn(), this.getEndLine(), this.getEndColumn());
			set_lops(cvlop) ;
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
		CrossvalOp ret = new CrossvalOp();	
		
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
