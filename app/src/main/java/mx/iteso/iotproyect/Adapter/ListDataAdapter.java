package mx.iteso.iotproyect.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

import mx.iteso.iotproyect.Models.Toppers;
import mx.iteso.iotproyect.R;

import static mx.iteso.iotproyect.R.drawable.circle_shape_green;
import static mx.iteso.iotproyect.R.drawable.circle_shape_ambar;
import static mx.iteso.iotproyect.R.drawable.circle_shape_red;

public class ListDataAdapter extends BaseAdapter {
    private Context context;
    private List<Toppers> list;
    private int color;

    public ListDataAdapter(Context Context, List<Toppers> List){
        this.context = Context;
        this.list = List;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup) {
         ViewHolder viewHolder;

         if(converView == null){
             converView = LayoutInflater.from(context).inflate(R.layout.itemsimple_row_datalist, null);
             viewHolder = new ViewHolder();
             viewHolder.EdgeRow = converView.findViewById(R.id.EdgeRow);
             viewHolder.ContainerName = converView.findViewById(R.id.ContainerName);
             viewHolder.LevelStatus = converView.findViewById(R.id.CircleDraw);

             converView.setTag(viewHolder);
         }else{
             viewHolder = (ViewHolder) converView.getTag();
         }

         Toppers topper = list.get(position);

         color = (topper.getType() == 0)?
                 context.getResources().getColor(R.color.colortypeTopper1,null):
                 (topper.getType() == 1)? context.getResources().getColor(R.color.colortypeTopper2, null):
                         context.getResources().getColor(R.color.colortypeTopper0,null);

         viewHolder.EdgeRow.setBackgroundColor(color);
         viewHolder.ContainerName.setText(topper.getName());

        switch (topper.getLevel()){
            case 1: viewHolder.LevelStatus.setImageResource(circle_shape_green);   break;
            case 2: viewHolder.LevelStatus.setImageResource(circle_shape_ambar);   break;
            case 3: viewHolder.LevelStatus.setImageResource(circle_shape_red);   break;
            default:
                viewHolder.LevelStatus.setVisibility(View.INVISIBLE);
        }

        return converView;
    }

    public class ViewHolder{
        LinearLayout EdgeRow;
        TextView ContainerName;
        ImageView LevelStatus;
    }
}
