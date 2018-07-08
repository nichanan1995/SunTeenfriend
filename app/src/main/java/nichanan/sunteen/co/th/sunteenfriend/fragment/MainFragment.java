package nichanan.sunteen.co.th.sunteenfriend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import nichanan.sunteen.co.th.sunteenfriend.R;
import nichanan.sunteen.co.th.sunteenfriend.ServiceActivity;
import nichanan.sunteen.co.th.sunteenfriend.utinity.GetAllData;
import nichanan.sunteen.co.th.sunteenfriend.utinity.MyConstant;
import nichanan.sunteen.co.th.sunteenfriend.utinity.Myalertdialog;

public class MainFragment extends Fragment{
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        registerController();

//        Login Controller
        loginController();
    }

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText usereditText = getView().findViewById(R.id.edtuser);
                EditText passwordEditect = getView().findViewById(R.id.edtpass);

                String userString = usereditText.getText().toString().trim();
                String passwordString = passwordEditect.getText().toString().trim();

                if (userString.isEmpty() || passwordString.isEmpty()){
                    Myalertdialog myalertdialog = new Myalertdialog(getActivity());
                    myalertdialog.normalDialog("Have space", "Please fill everyblank");

                }else {
                    Myalertdialog myalertdialog = new Myalertdialog(getActivity());
                    MyConstant myConstant = new MyConstant();
                    boolean aboolean = true;
                    String truePassword = null;


                    try {
                        GetAllData getAllData = new GetAllData(getActivity());

                        getAllData.execute(myConstant.getUrlGetAllUser());
                        String jsonString = getAllData.get();
                        Log.d("8July","JSON ==>" + jsonString);

                        JSONArray jsonArray = new JSONArray(jsonString);
                        for (int i=0; i<jsonArray.length(); i+=1) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (userString.equals(jsonObject.getString("User"))) {
                                truePassword = jsonObject.getString("Password");
                                aboolean = false;
                            }

                        }
                        if (aboolean) {
                            myalertdialog.normalDialog("User False", "No" + userString + "in my Database");

                        } else if (passwordString.equals(truePassword)) {
//                            passwordtrue
                            Toast.makeText(getActivity(), "welcome to my App", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getActivity(), ServiceActivity.class));
                            getActivity().finish();
                        } else {
                            myalertdialog.normalDialog("Password False", "Please try Again Passsword False");
                        }



                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }// if



            }
        });
    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container, false);
        return view;
    }
}   //Main Class
