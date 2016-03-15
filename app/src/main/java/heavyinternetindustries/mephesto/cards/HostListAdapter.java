package heavyinternetindustries.mephesto.cards;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Created by mephest0 on 15.03.16.
 */
public class HostListAdapter extends ArrayAdapter<WifiP2pDevice> {
    private final MainActivity activity;

    public HostListAdapter(MainActivity activity) {
        super(activity, R.layout.host_list_item);

        this.activity = activity;
    }

    @Override
    public int getCount() {
        return activity.wifiDevices.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WifiP2pDevice device = activity.wifiDevices.get(position);
        LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ret = li.inflate(R.layout.host_list_item, null);

        TextView tv = (TextView) ret.findViewById(R.id.title);

        tv.setText("Name: " + device.deviceName);

        return ret;
    }
}
