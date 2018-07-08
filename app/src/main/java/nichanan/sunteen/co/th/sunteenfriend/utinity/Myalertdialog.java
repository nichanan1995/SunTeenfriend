package nichanan.sunteen.co.th.sunteenfriend.utinity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import nichanan.sunteen.co.th.sunteenfriend.R;

public class Myalertdialog {


       private Context context;

    public Myalertdialog(Context context) {
        this.context = context;
    }

    public void normalDialog(String titleString, String detailString) {
           AlertDialog.Builder builder = new AlertDialog.Builder(context);
           builder.setCancelable(false);
           builder.setIcon(R.drawable.ic_action_alert);
           builder.setTitle(titleString);
           builder.setMessage(detailString);
           builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   dialogInterface.dismiss();
               }
           });
           builder.show();
       }
   }

