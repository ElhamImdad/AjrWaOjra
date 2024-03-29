package com.example.smoot.ajerwaojra.Fragments;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Models.Doer;
import com.example.smoot.ajerwaojra.R;

public class FatwaFragment extends Fragment {
    LinearLayout doer, requester ;
    Fragment doerFrag;
    Fragment requesterFrag;
    TextView advisoryOpinion, doOmra, talabOmra;
    LocationManager locationManager;
    CheckBox checked ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_fatwa, container, false);
        checked = view.findViewById(R.id.checkBox);
        doer = view.findViewById(R.id.doer);
        requester = view.findViewById(R.id.requester);
        doOmra = view.findViewById(R.id.textDoOmra);
        talabOmra = view.findViewById(R.id.textTalabOmra);
        advisoryOpinion = view.findViewById(R.id.advisoryOpinion);
        advisoryOpinion.setText("فإن الحج عن الميت والعمرة عن الميت من أفضل القربات، وينتفع بها الميت المسلم كثيرًا، فقد سئل النبي ﷺ عن ذلك مرات كثيرة فقال للسائل: حج عن أبيك، وللسائلة: حجي عن أبيك، والآخر: عن أمك، وسأله آخر قال: إني لبيت عن شبرمة قال: من شبرمة؟ قال: أخ لي أو قريب لي. قال: حج عن نفسك ثم حج عن شبرمة." +
                "فالناس أقسام منهم من قد حج الفريضة وأدى العمرة الفريضة هذا إذا حج عنه يكون نافلة، وإذا اعتمر عنه يكون نافلة، حج عنه أخوه أو أبوه أو قريب له أو أخ من إخوانه في الله كل ذلك طيب وهكذا العمرة، وإذا كان ما أدى الحج ولا أدى العمرة فإن الذي يحج عنه يكون قد أدى عنه الفريضة، وهكذا العمرة يكون قد أدى عنه عمرة الفريضة، وهو على كل حال مأجور والميت مأجور كلاهما مأجور، هذا عن عمله الطيب وإحسانه إلى أخيه مأجور والميت مأجور بذلك، وهكذا الصدقة وهكذا الدعاء إذا تصدق عن أخيه يؤجر هو والميت جميعًا، وهكذا إذا دعا لأخيه الميت يؤجر هو وينتفع الميت بالدعاء. نعم.");
         // if the user is logged in so go to home page
        // TODO : change the fragment from requester to doer
        if(SharedPrefManager.getInstance(getContext()).isLoggedIn()){
        //    SharedPrefManager.getInstance(getContext()).logout();
            Doer user = SharedPrefManager.getInstance(getContext()).getDoer();
           Fragment f = new logInFragment();
           android.support.v4.app.FragmentManager fm = getFragmentManager();
           FragmentTransaction ft = fm.beginTransaction();
           ft.replace(R.id.container, f);
           ft.commit();
        }
        // if the user is not logged in so go



        doerFrag = new DoerRegistrationFragment();
        doer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMessage();
              /*  doerFrag = new DoerRegistrationFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, doerFrag);
                ft.commit();*/
            }
        });

        requester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checked.isChecked()){
                 readFatwaMessage();
                }
                else
                {
                    requester.setBackgroundResource(R.drawable.rectangle_greentab);
                    talabOmra.setTextColor(getResources().getColor(R.color.white));

                    doer.setBackgroundResource(R.drawable.rectangle);
                    doOmra.setTextColor(getResources().getColor(R.color.darkGray));
                requesterFrag = new RequesterRegistrationFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, requesterFrag);
                ft.addToBackStack(null);
                ft.commit();}
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    public void showMessage(){
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        AlertDialog.Builder  alert = new AlertDialog.Builder(getContext());
        alert.setTitle("صلاحيات الوصول......");
        alert.setMessage("يجب أن يصل التطبيق الى موقعك الحالي ");
        alert.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doer.setBackgroundResource(R.drawable.rectangle_greentab);
                doOmra.setTextColor(getResources().getColor(R.color.white));

                requester.setBackgroundResource(R.drawable.rectangle);
                talabOmra.setTextColor(getResources().getColor(R.color.darkGray));
               /* if ( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    // if the GPS is not enabled
                    Log.e("i am in if", "hi ");
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    goDoerRegistration();
                }
                else
                {
                    // the GPS is enabled so go to Doer Registration fragment
                    goDoerRegistration();
                }*/
                goDoerRegistration();

            }
        });
        alert.show();
    }
    public void readFatwaMessage(){
        AlertDialog.Builder  alert = new AlertDialog.Builder(getContext());
        alert.setTitle("عذرا......");
        alert.setMessage("يجب الاطلاع على الفتوى ");
        alert.show();
    }
    public void goDoerRegistration(){
        doerFrag = new DoerRegistrationFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, doerFrag);
        ft.addToBackStack(null);
        ft.commit();
    }
}


