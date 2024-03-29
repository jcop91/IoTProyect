package mx.iteso.iotproyect.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

import mx.iteso.iotproyect.Models.StringsClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO(Clase) Se crea para ejecutar la personalizacion de un funcion Callback se agrega
// un cuadro de dialogo con un barra de progreso del tipo circular.
public class CustomCallBack<T> implements Callback<T> {

    private ProgressDialog mProgressDialog;
    Context context;

    //TODO(Metodo) Constructor para crear el recuadro de espera (ProgressDialog).
    protected CustomCallBack(Context context) {
        this.context = context;
        mProgressDialog = new ProgressDialog(context);
        ((Activity) context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(StringsClass.MessageLoadingInfo);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }
}

