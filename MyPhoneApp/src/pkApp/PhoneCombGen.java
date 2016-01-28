/***Original Author: Pratima Kshetry
Date Created: Jan 18, 2016***/

package pkApp;

import java.util.Vector;

public class PhoneCombGen {

	//Map for phone digits
	private String[] sAlpha={"","","ABC","DEF","GHI","JKL","MNO","PQRS","TUV","WXYZ"};
	private void genCombination(Vector<String> v1,String sNum,int digit)
	{
		try
		{		
			int num=Integer.parseInt(sNum);
			String replace= sAlpha[num];
			int nCount= replace.length();
			int nSize=v1.size();		
			for(int i=0;i<nSize;i++)
			{
			    String s=v1.elementAt(i);
			    String pre= s.substring(0, digit);
			    String post= s.substring(digit+1, s.length());
				for(int j=0;j<nCount;j++)
				{	
					String sItem=pre;
					sItem+=replace.charAt(j);	
					sItem+=post;
					v1.addElement(sItem);
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public Vector<String> generate(String sPhoneNum)
	{
		Vector<String> v=new Vector<String>();		
		v.add(sPhoneNum);
		for(int i=sPhoneNum.length()-1;i>=0;i--)
		{
			genCombination(v,""+sPhoneNum.charAt(i),i);			
		}		
		/*for(String s:v)
		{
			System.out.println(s);
		}*/	
		return v;		
	}

}
