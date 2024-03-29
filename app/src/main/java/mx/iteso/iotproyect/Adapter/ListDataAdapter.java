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

import mx.iteso.iotproyect.Models.ToppersDB;
import mx.iteso.iotproyect.R;

import static mx.iteso.iotproyect.R.drawable.circle_shape_green;
import static mx.iteso.iotproyect.R.drawable.circle_shape_ambar;
import static mx.iteso.iotproyect.R.drawable.circle_shape_red;

//TODO(Clase): Adaptador para la vista personalizada de la lista
public class ListDataAdapter extends BaseAdapter {
    private Context context;
    private List<ToppersDB> list;
    private int color, colorLevel;

    public ListDataAdapter(Context Context, List<ToppersDB> List){
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

    //TODO(Metodo): En esta parte entra un elemento de la lista y se adapta con los valores
    // a la estructura de cada fila de la lista.
    @Override
    public View getView(int position, View converView,  ViewGroup viewGroup) {
         ViewHolder viewHolder;

         if(converView == null){
             converView = LayoutInflater.from(context).inflate(R.layout.itemsimple_row_datalist, viewGroup,false);
             viewHolder = new ViewHolder();
             viewHolder.EdgeRow = converView.findViewById(R.id.EdgeRow);
             viewHolder.ContainerName = converView.findViewById(R.id.ContainerName);
             viewHolder.LevelStatus = converView.findViewById(R.id.CircleDraw);

             converView.setTag(viewHolder);
         }else{
             viewHolder = (ViewHolder) converView.getTag();
         }

         final ToppersDB topper = list.get(position);

         color = (topper.getType() == 0)?
                 context.getResources().getColor(R.color.colortypeTopper1,null):
                 (topper.getType() == 1)? context.getResources().getColor(R.color.colortypeTopper2, null):
                         context.getResources().getColor(R.color.colortypeTopper0,null);

         viewHolder.EdgeRow.setBackgroundColor(color);
         viewHolder.ContainerName.setText(topper.getName());

         if(topper.getLevel() == 0){
             viewHolder.LevelStatus.setVisibility(View.INVISIBLE);
         }else{
             viewHolder.LevelStatus.setVisibility(View.VISIBLE);

             colorLevel =(topper.getLevel() == 1)? circle_shape_green:
                     (topper.getLevel() == 2)? circle_shape_ambar:
                             circle_shape_red;
             viewHolder.LevelStatus.setImageResource(colorLevel);
         }

        return converView;
    }

    public class ViewHolder{
        LinearLayout EdgeRow;
        TextView ContainerName;
        ImageView LevelStatus;
    }
}
