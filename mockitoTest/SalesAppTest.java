package sales;

import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(JMockit.class)
public class SalesAppTest {

	@Test
	public void testCheckDate(){
		// given
		SalesApp salesApp = spy(new SalesApp());
		Sales sales = spy(new Sales());

		new mockit.MockUp<SalesDao>(){
			@mockit.Mock
			public Sales getSalesBySalesId
					(String salesId){
				return sales;
			}
		};
		doReturn(new Date(System.currentTimeMillis() +  24 * 60 * 60 * 1000)).when(sales).getEffectiveFrom();
		doReturn(new Date(0)).when(sales).getEffectiveTo();

		// then
		Assert.assertEquals(true, salesApp.checkDate(sales));
	}

	@Test
	public void testGenerateFilteredReportDataList(){
		// given
		SalesApp salesApp = spy(new SalesApp());
		SalesReportDao salesReportDao = spy(new SalesReportDao());
		SalesReportData salesReportData = spy(new SalesReportData());
		salesReportData.setConfidential(true);

		new mockit.MockUp<SalesReportDao>(){
			@mockit.Mock
			public List<SalesReportData> getReportData(Sales sales){
				return Arrays.asList(salesReportData);
			}
		};

		new mockit.MockUp<SalesApp>(){
			@mockit.Mock
			public List<SalesReportData> initTempList
					(int maxRow, List<SalesReportData> reportDataList){
				return null;
			}
		};

		doReturn("SalesActivity").when(salesReportData).getType();
		doReturn(false).when(salesApp).checkDate(any());
		doReturn("Null").when(salesApp).getXml(any());

		// when
		salesApp.generateSalesActivityReport("DUMMY", 1000, false, true);

		// then
		verify(salesApp,times(1)).generateFilteredReportDataList(true,Arrays.asList(salesReportData));
	}

	@Test
	public void testInitTempList(){
		// given
		SalesApp salesApp = spy(new SalesApp());
		doReturn(false).when(salesApp).checkDate(any());
		SalesReportData salesReportData = spy(new SalesReportData());
		salesReportData.setConfidential(true);

		new mockit.MockUp<SalesReportDao>(){
			@mockit.Mock
			public List<SalesReportData> getReportData(Sales sales){
				return Arrays.asList(salesReportData);
			}
		};

		doReturn("SalesActivity").when(salesReportData).getType();
		doReturn(false).when(salesApp).checkDate(any());
		doReturn("Null").when(salesApp).getXml(any());
		// when
//		salesApp.generateSalesActivityReport("DUMMY", 1000, false, true);
		// then
//		verify(salesApp,times(1)).initTempList(any(),anyList());
		Assert.assertEquals(true, salesApp.initTempList(1, Arrays.asList(salesReportData)).get(0).isConfidential());
	}

	@Test
	public void testCheckNatTrade(){
		// given
		SalesApp salesApp = spy(new SalesApp());

		// when
		// then
		Assert.assertEquals(Arrays.asList("Sales ID", "Sales Name", "Activity", "Time"),salesApp.checkNatTrade(true));
		Assert.assertEquals(Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time"), salesApp.checkNatTrade(false));
	}

	@Test
	public void testGenerateReport() {
		// given
		SalesApp salesApp = spy(new SalesApp());

		new mockit.MockUp<SalesApp>(){
			@mockit.Mock
			public List<SalesReportData> generateFilteredReportDataList
					(boolean isSupervisor,  List<SalesReportData> reportDataList){
				return null;
			}
		};

		new mockit.MockUp<SalesApp>(){
			@mockit.Mock
			public List<SalesReportData>  initTempList
					(int maxRow, List<SalesReportData> reportDataList){
				return null;
			}
		};

		doReturn(false).when(salesApp).checkDate(any());
		doReturn("Null").when(salesApp).getXml(any());

		// when
		salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);

		// then
		verify(salesApp,times(1)).generateReport(any(), any());
	}

	@Test
	public void testGetXml(){
		// given
		SalesApp salesApp = spy(new SalesApp());

		new mockit.MockUp<SalesApp>(){
			@mockit.Mock
			public List<SalesReportData> generateFilteredReportDataList
					(boolean isSupervisor,  List<SalesReportData> reportDataList){
				return null;
			}
		};

		new mockit.MockUp<SalesApp>(){
			@mockit.Mock
			public List<SalesReportData>  initTempList
					(int maxRow, List<SalesReportData> reportDataList){
				return null;
			}
		};

		doReturn(false).when(salesApp).checkDate(any());

		// when
		try{
			salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);
		}catch (Exception e){
			Assert.assertEquals(NullPointerException.class, e.getClass());
		}

		// then
		verify(salesApp,times(1)).getXml(any());
	}



}
