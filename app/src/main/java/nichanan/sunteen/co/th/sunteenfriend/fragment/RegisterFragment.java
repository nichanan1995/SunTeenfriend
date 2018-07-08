package nichanan.sunteen.co.th.sunteenfriend.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import nichanan.sunteen.co.th.sunteenfriend.MainActivity;
import nichanan.sunteen.co.th.sunteenfriend.R;
import nichanan.sunteen.co.th.sunteenfriend.utinity.Myalertdialog;

public class RegisterFragment extends Fragment {
    //    Explicit
    private Uri uri;
    private ImageView imageView;
    private boolean aBoolean = true;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Create toolbar
        createToolbar();

        pictureController();

    }//Main method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemUpload) {
            uploadAnupdateValue();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadAnupdateValue() {

        Myalertdialog myalertdialog = new Myalertdialog(getActivity());
        EditText nameEditText = getView().findViewById(R.id.editname);
        EditText userEditText = getView().findViewById(R.id.edtuser);
        EditText passEditText = getView().findViewById(R.id.edtpass);
//        Get Value to String

        String nameString = nameEditText.getText().toString().trim();
        String userString = userEditText.getText().toString().trim();
        String passString = passEditText.getText().toString().trim();

        //check choose image and space
        if (aBoolean) {

            myalertdialog.normalDialog("none choose image",
                    "pleas chose");


        } else if (nameString.isEmpty() || userString.isEmpty() || passString.isEmpty()) {
//         //   Have space
                    myalertdialog.normalDialog("have space",
                            "please fill all bank");
        }
             else {
//            No Space
            uploadImage();
        }

    }//upload

    private void uploadImage() {
//        find path Image
        String pathString =null;
        String[] strings = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, strings, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            pathString = cursor.getString(index);


    }else{
            pathString = uri.getPath();

        }
        Log.d("8Julyv1", "Path ==>" + pathString);
    }


    private void pictureController() {
        imageView = getView().findViewById(R.id.imvPicture);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "choose app"), 1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == getActivity().RESULT_OK) {


//            success choose Image
            uri = data.getData();
//            Show Image_on ImageView

            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(getActivity(), "please Choose Image", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_register, menu);

    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

//        Setup Toolbar
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.register));
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("fill Bill All blank");

//        Set up navigator icon
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }
}
