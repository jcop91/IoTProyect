package mx.iteso.iotproyect.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mx.iteso.iotproyect.R;

public class AlertAdapter implements View.OnClickListener {
    private Context context;
    private  String textButton;
    private String textMessage;
    private  Drawable draw;
    private Thread thread;

    public AlertAdapter(Context Context, String TextButton, String TextMessage, Drawable Draw){
        this.context = Context;
        this.textButton = TextButton;
        this.textMessage = TextMessage;
        this.draw = Draw;
    }

    public View getView(View converView){
        ViewHoldes viewHoldes;
        if(converView == null){
            converView = LayoutInflater.from(context).inflate(R.layout.item_dialog_menu,null);
            viewHoldes = new ViewHoldes();
            viewHoldes.Message = converView.findViewById(R.id.tvMessageAlert);
            viewHoldes.btn = converView.findViewById(R.id.addTopperMenuConfig);
            converView.setTag(viewHoldes);
        }else{
            viewHoldes = (ViewHoldes) converView.getTag();
        }

        viewHoldes.Message.setText(textMessage);
        viewHoldes.btn.setText(textButton);
        draw.setBounds(0,0,60,60);
        viewHoldes.btn.setCompoundDrawables(draw,null,null,null);
        viewHoldes.btn.setOnClickListener(this);

        return converView;
    }

    @Override
    public void onClick(View view) {
        thread.start();
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public class ViewHoldes{
        Button btn;
        TextView Message;
    }
}
