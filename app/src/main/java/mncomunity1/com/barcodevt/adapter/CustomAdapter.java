package mncomunity1.com.barcodevt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mncomunity1.com.barcodevt.R;
import mncomunity1.com.barcodevt.model.Machine;

public class CustomAdapter extends BaseAdapter {

    private Context activity;
    private ArrayList<Machine> data;
    private static LayoutInflater inflater = null;
    private View vi;
    private ViewHolder viewHolder;

    public CustomAdapter(Context context, ArrayList<Machine> items) {
        this.activity = context;
        this.data = items;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        vi = view;
        final int pos = position;
        Machine items = data.get(pos);
        if (view == null) {
            vi = inflater.inflate(R.layout.listview_row, null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) vi.findViewById(R.id.checkbox);
            viewHolder.name = (TextView) vi.findViewById(R.id.txt_titles);
            vi.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();
        viewHolder.name.setText(items.getNameTh());
        if (items.isBox()) {
            viewHolder.checkBox.setChecked(true);
        } else {
            viewHolder.checkBox.setChecked(false);
        }
        return vi;
    }

    public ArrayList<Machine> getAllData() {
        return data;
    }

    public void setCheckBox(int position) {
        Machine items = data.get(position);
        items.setBox(!items.isBox());
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView name;
        CheckBox checkBox;
    }
}