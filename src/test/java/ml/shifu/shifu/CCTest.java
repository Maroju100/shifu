package ml.shifu.shifu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;

import ml.shifu.shifu.container.obj.ColumnConfig;
import ml.shifu.shifu.container.obj.RawSourceData.SourceType;
import ml.shifu.shifu.util.CommonUtils;
import ml.shifu.shifu.util.JSONUtils;

public class CCTest {

    @Test
    public void testRead() throws IOException {
        Scanner sc = new Scanner(new FileInputStream("src/test/resources/f1484-2.txt"));
        String line = null;

        Set<String> columns = new HashSet<String>();
        while(sc.hasNext()) {
            line = sc.nextLine();
            if(line != null && line.length() > 0) {
                System.out.println(line);
                String column = line.split("\\|")[0];
                columns.add(column);
            }
        }

        sc.close();

        List<ColumnConfig> list = CommonUtils.loadColumnConfigList("src/test/resources/ColumnConfig1.json",
                SourceType.LOCAL);
        int cnt = 0;
        List<String> names = new ArrayList<String>();
        for(ColumnConfig config: list) {
            names.add(config.getColumnName());
            if(!config.isTarget() && !config.isMeta()) {
                config.setFinalSelect(false);
                if(columns.contains(config.getColumnName())) {
                    config.setFinalSelect(true);
                    cnt += 1;
                }
            }
        }

        Collections.sort(names);
        for(String name: names) {
            System.out.println(name);
        }

        System.out.println(cnt);

        JSONUtils.writeValue(new File("src/test/resources/ColumnConfig-1484-2.json"), list);
    }
}
