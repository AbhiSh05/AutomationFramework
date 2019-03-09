package Utils;

import BaseTest.PageTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.T;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CreatingEmailTemplate extends PageTest {
    public static String ApplicationName=CONSTANTS.ApplicationName;
    public static String ModuleName=CONSTANTS.ModuleName;
    public static int TotalNumber=0;
    public static int Pass=0;
    public static int Fail=0;
    public static String value="";

    public void createEmailTemplate() throws Exception {
        int index=0;
        Map<String, String> map = ExcelUtils.GetResults();
        TotalNumber = TotalNumber + map.size();
        for (Map.Entry temp : map.entrySet()) {
            index++;
            if (temp.getValue().equals("Pass")) {
                Pass++;
                value = value + "<tr><td>"+index+"</td><td>" + temp.getKey() + "</td><td>" + temp.getValue() + "</td></tr>";
            } else {
                Fail++;
                value = value + "<tr><td>"+index+"</td><td>" + temp.getKey() + "</td><td>" + temp.getValue() + "</td></tr>";
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        long duration = PageTest.endDate.getTime() - PageTest.startDate.getTime();
        long diffSeconds = duration / 1000 % 60;
        long diffMinutes = duration / (60 * 1000) % 60;
        long diffHours = duration / (60 * 60 * 1000);
        String durationString= diffHours+":"+diffMinutes+":"+diffSeconds;
        DecimalFormat dcm = new DecimalFormat("0.00");
        double passpercentage=(Pass/TotalNumber)*100.;
        System.out.println(passpercentage);
        String passperc= dcm.format(passpercentage);
        System.out.println(passperc);
        double failpercentage= 100.00- Float.valueOf(passperc);
        System.out.println(failpercentage);
        String failperc= dcm.format(failpercentage);
        System.out.println(failperc);
        String fileContent= new String(Files.readAllBytes(Paths.get(CONSTANTS.EmailTemplate)));
        String newString= fileContent.replace("#APP",ApplicationName).replace("#MOD",ModuleName).replace("#STR",sdf.format(PageTest.startDate)).replace("#END",sdf.format(PageTest.endDate)).replace("#DUR",durationString).replace("#NO",String.valueOf(TotalNumber)).replace("#PASS",String.valueOf(Pass)).replace("#FAIL",String.valueOf(Fail)).replace("#TABLE",value).replace("#PERCENTAGE1",passperc+" %").replace("#PERCENTAGE2",failperc+" %");
        creatingEmailExecutionReport(newString);
    }

    public void creatingEmailExecutionReport(String data) throws Exception {
        System.out.println(data);
        File htmlFile= new File(CONSTANTS.JenkinsEmailFile);
        FileUtils.writeStringToFile(htmlFile,data);
        logger.log(LogStatus.PASS,"Email Execution Report has been generated");
    }


}
