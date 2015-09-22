package <%= appPackage %>.util.flow;

import java.util.Iterator;

import flow.History;

public class FlowHistoryDevHelper {

    public static String flowHistoryToString(History history) {
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        Iterator<Object> iterator = history.iterator();
        stringBuilder.append('\n');
        while (iterator.hasNext()) {
            Object next = iterator.next();
            stringBuilder.append(count++).append(" <==> ");
            stringBuilder.append(next.toString());
            stringBuilder.append('\n');
        }
        stringBuilder.append('\n');
        return stringBuilder.toString();
    }
}
