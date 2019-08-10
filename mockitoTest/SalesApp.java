package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

	public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {
		
		SalesDao salesDao = new SalesDao();
		SalesReportDao salesReportDao = new SalesReportDao();
		List<String> headers = null;
		
		List<SalesReportData> filteredReportDataList = new ArrayList<SalesReportData>();
		
		if (salesId == null) {
			return;
		}
		
		Sales sales = salesDao.getSalesBySalesId(salesId);

		if (checkDate(sales)) return;

		List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);

		filteredReportDataList = this.generateFilteredReportDataList(isSupervisor, reportDataList);

		List<SalesReportData> tempList = new ArrayList<SalesReportData>();

                tempList = initTempList(maxRow, reportDataList);

		filteredReportDataList = tempList;

		headers = checkNatTrade(isNatTrade);

		SalesActivityReport report = this.generateReport(headers, reportDataList);
		EcmService ecmService = new EcmService();
		ecmService.uploadDocument(getXml(report));
		
	}

	public boolean checkDate(Sales sales) {
		Date today = new Date();
		if (today.after(sales.getEffectiveTo())
				|| today.before(sales.getEffectiveFrom())){
			return true;
		}
		return false;
	}

	public List<SalesReportData> generateFilteredReportDataList(boolean isSupervisor, List<SalesReportData> reportDataList) {
		List<SalesReportData> filteredReportDataList = new ArrayList<>();
		for (SalesReportData data : reportDataList) {
			if ("SalesActivity".equalsIgnoreCase(data.getType())) {
				if (data.isConfidential()) {
					if (isSupervisor) {
						filteredReportDataList.add(data);
					}
				}else {
					filteredReportDataList.add(data);
				}
			}
		}
		return filteredReportDataList;
	}

        public List<SalesReportData> initTempList(int maxRow, List<SalesReportData> reportDataList) {
	    List<SalesReportData> tempList = new ArrayList<>();
            for (int i=0; i < reportDataList.size() || i < maxRow; i++) {
                tempList.add(reportDataList.get(i));
            }
            return tempList;
        }

	public List<String> checkNatTrade(boolean isNatTrade) {
		List<String> headers;
		if (isNatTrade) {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
		} else {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
		}
		return headers;
	}

	public SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getXml(SalesActivityReport report) {
		return report.toXml();
	}

}
