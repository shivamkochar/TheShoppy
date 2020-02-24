package com.example.theshoppy;

import android.text.InputType;
import android.view.View;

import android.app.DatePickerDialog;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //declaring the variables
    private String[] province = {"Ontario", "Quebec", "Alberta" ,"Saskatchewan", "Manitoba", "New Brunswick", "Nova Scotia"};
    private AppCompatAutoCompleteTextView autoTextView;
    private String[] brands = {"Dell", "HP", "Lenova"};
    private Spinner spnBrand;
    DatePickerDialog dopPicker;
    EditText edtDate;

    EditText txtName;

    ScrollView scrollLaptop, scrollComputer;
    TextView txtInvoice;
    Button btnInvoice;

    RadioGroup radioGroupChoice;
    RadioButton rbLaptop;
    RadioButton rbComputer;

    CheckBox checkSsd;
    CheckBox checkPrinter;

    RadioGroup radioGroupLaptopPeripheral;
    RadioButton rbCoolingMat;
    RadioButton rbUsb;
    RadioButton rbLaptopStand;

    LinearLayout linearLaptopPeripheral, linearDesktopPeripheral, linearInvoice;

    RadioGroup radioGroupDesktopPeripheral;
    RadioButton rbNone;
    RadioButton rbWebcam;
    RadioButton rbHardDrive;

    String name;
    String txtProvince;
    String date;
    String typeOfComputer;
    String txtBrands;
    String txtItemSsd;
    String txtItemPrinter;
    String txtExtraItem;
    String txtExtraPeripheral;

    int laptopPrice, desktopPrice, extraItems, laptopPeripherals, desktopPeripherals;
    double  taxAmount, totalAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // checking which brand is selected from the spinner
        spnBrand = (Spinner)findViewById(R.id.spinnerBrand);
        ArrayAdapter<String> adptBrands = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brands);
        adptBrands.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBrand.setAdapter(adptBrands);

        //initializing radioGroup and radio buttons for laptop and computer
        radioGroupChoice = (RadioGroup)findViewById(R.id.radioGroupChoice);
        rbLaptop = (RadioButton)findViewById(R.id.radioBtnLaptop);
        rbComputer = (RadioButton)findViewById(R.id.radioBtnComp);

        //initializing scroll views on the basis of computer type
        scrollLaptop = (ScrollView)findViewById(R.id.scrollLaptop);
        scrollComputer = (ScrollView)findViewById(R.id.scrollComputer);

        //initializing peripherals for computer and desktop
        linearDesktopPeripheral = (LinearLayout)findViewById(R.id.linearDesktopPeripheral);
        linearLaptopPeripheral = (LinearLayout)findViewById(R.id.linearLaptopPeripheral);
        linearInvoice = (LinearLayout)findViewById(R.id.LinearInvoice);

        //initializing radio group laptop peripheral and radio button for laptop peripherals
        radioGroupLaptopPeripheral = (RadioGroup)findViewById(R.id.radioGrpLaptop);
        rbCoolingMat = (RadioButton)findViewById(R.id.rbCoolingMat);
        rbUsb = (RadioButton)findViewById(R.id.rbUsb);
        rbLaptopStand = (RadioButton)findViewById(R.id.rbLaptopStand);

        //initializing radio group desktop peripheral and radio button for desktop peripherals
        radioGroupDesktopPeripheral = (RadioGroup)findViewById(R.id.radioGrpDesktop);
        rbNone = (RadioButton)findViewById(R.id.rbNone);
        rbWebcam = (RadioButton)findViewById(R.id.rbWebcam);
        rbHardDrive = (RadioButton)findViewById(R.id.rbHardDrive);

        //Initializing textName, textInvoice and button invoice
        txtName = (EditText)findViewById(R.id.txtName);
        txtInvoice = (TextView)findViewById(R.id.txtInvoice);
        btnInvoice = (Button)findViewById(R.id.btnTotal);

        //Added listener to check which scroll view to be displayed according to the radio button selected
        radioGroupChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)findViewById(checkedId);
                // to check which radio button is selected
                if(rb.getId() == R.id.radioBtnLaptop)
                {
                    //setting the visibility of laptop scrollview to true
                    scrollLaptop.setVisibility(View.VISIBLE);
                    //setting the visibility of desktop scrollview to false
                    scrollComputer.setVisibility(View.GONE);

                    //setting the visibility of laptop peripheral to true
                    linearLaptopPeripheral.setVisibility(View.VISIBLE);
                    //setting the visibility of desktop peripheral to false
                    linearDesktopPeripheral.setVisibility(View.GONE);

                    //getting the selected value of radio button laptop in the typeOfComputer
                    typeOfComputer = rbLaptop.getText().toString();
                }
                else if(rb.getId() == R.id.radioBtnComp)
                {
                    //setting the visibility of desktop scrollview to true
                    scrollComputer.setVisibility(View.VISIBLE);
                    //setting the visibility of laptop scrollview to false
                    scrollLaptop.setVisibility(View.GONE);

                    //setting the visibility of desktop peripheral to true
                    linearDesktopPeripheral.setVisibility(View.VISIBLE);
                    //setting the visibility of laptop peripheral to false
                    linearLaptopPeripheral.setVisibility(View.GONE);

                    //getting the selected value of radio button laptop in the typeOfComputer
                    typeOfComputer = rbComputer.getText().toString();
                }
            }
        });

        //Added listener to check which peripheral is selected for laptop among the given peripherals
        radioGroupLaptopPeripheral.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)findViewById(checkedId);

                if(rb.getId() == R.id.rbCoolingMat)
                {
                    txtExtraPeripheral = rbCoolingMat.getText().toString();
                }
                else if(rb.getId() == R.id.rbUsb)
                {
                    txtExtraPeripheral = rbUsb.getText().toString();
                }
                else if(rb.getId() == R.id.rbLaptopStand)
                {
                    txtExtraPeripheral = rbLaptopStand.getText().toString();
                }
            }
        });

        //Added listener to check which peripheral is selected for desktop among the given peripherals
        radioGroupDesktopPeripheral.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)findViewById(checkedId);

                if(rb.getId() == R.id.rbNone)
                {
                    txtExtraPeripheral = rbNone.getText().toString();
                }
                else if(rb.getId() == R.id.rbWebcam)
                {
                    txtExtraPeripheral = rbWebcam.getText().toString();
                }
                else if(rb.getId() == R.id.rbHardDrive)
                {
                    txtExtraPeripheral = rbHardDrive.getText().toString();
                }
            }
        });

        //Displaying the app title in the app bar on the top of the view space
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_title);

        //To complete the province when user enter first two characters of province in the EditText
        autoTextView = (AppCompatAutoCompleteTextView) findViewById(R.id.autoTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, R.layout.support_simple_spinner_dropdown_item, province);
        //Setting the threshold value to 2 so that suggestion is made when two characters is entered by the user
        autoTextView.setThreshold(2);
        autoTextView.setAdapter(adapter);

        //Initializing EditText for the date of purchase
        edtDate = (EditText) findViewById(R.id.edtDop);
        edtDate.setInputType(InputType.TYPE_NULL);

        //Setting the listener to get the date from the calendar view
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                String dateSelected;
                //date picker dialog
                dopPicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dopPicker.show();
                dateSelected = edtDate.getText().toString();
            }
        });

        //Setting the OnClick listener on button to show the final invoice
       btnInvoice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               name = txtName.getText().toString();
               txtProvince = autoTextView.getText().toString();
               date = edtDate.getText().toString();
               txtBrands = spnBrand.getSelectedItem().toString();

               linearInvoice.setVisibility(View.VISIBLE);

               //Checking if the Dell laptop is Selected or not & setting the desktop peripherals to false
               if(rbLaptop.isChecked() == true && spnBrand.getSelectedItem().toString() == "Dell" )
               {
                   rbNone.setChecked(false);
                   rbHardDrive.setChecked(false);
                   rbWebcam.setChecked(false);

                   totalAmount = 1249;
               }
               //Checking if the HP laptop is Selected or not & setting the desktop peripherals to false
               else if(rbLaptop.isChecked()== true && spnBrand.getSelectedItem().toString() == "HP")
               {
                   rbNone.setChecked(false);
                   rbHardDrive.setChecked(false);
                   rbWebcam.setChecked(false);

                   totalAmount = 1150;
               }
               //Checking if the Lenova laptop is Selected or not & setting the desktop peripherals to false
               else if(rbLaptop.isChecked()== true && spnBrand.getSelectedItem().toString() == "Lenova")
               {
                   rbNone.setChecked(false);
                   rbHardDrive.setChecked(false);
                   rbWebcam.setChecked(false);

                   totalAmount = 1549;
               }
               //Checking if the Dell desktop is Selected or not & setting the laptop peripherals to false
               else if(rbComputer.isChecked()== true && spnBrand.getSelectedItem().toString()== "Dell")
               {
                   rbCoolingMat.setChecked(false);
                   rbUsb.setChecked(false);
                   rbLaptopStand.setChecked(false);

                   totalAmount = 475;
               }
               //Checking if the HP desktop is Selected or not & setting the laptop peripherals to false
               else if(rbComputer.isChecked()== true && spnBrand.getSelectedItem().toString() == "HP")
               {
                   rbCoolingMat.setChecked(false);
                   rbUsb.setChecked(false);
                   rbLaptopStand.setChecked(false);

                   totalAmount = 400;
               }
               //Checking if the Lenova desktop is Selected or not & setting the laptop peripherals to false
               else if(rbComputer.isChecked()== true && spnBrand.getSelectedItem().toString()== "Lenova")
               {
                   rbCoolingMat.setChecked(false);
                   rbUsb.setChecked(false);
                   rbLaptopStand.setChecked(false);

                   totalAmount = 450;
               }

               //Initializing the ssd and printer checkbox
               checkSsd = (CheckBox)findViewById(R.id.chkSsd);
               checkPrinter = (CheckBox)findViewById(R.id.chkPrinter);

               //Checking if both printer and ssd is selected or not and adding the amount
               if(checkSsd.isChecked() && checkPrinter.isChecked())
               {
                   txtItemSsd = checkSsd.getText().toString();
                   txtItemPrinter = checkPrinter.getText().toString();
                   txtExtraItem = txtItemSsd + " & " + txtItemPrinter;
                    totalAmount = totalAmount + 60 + 100;
               }
               //Checking if only ssd is selected and adding the ssd amount to the final cost
               else if(checkSsd.isChecked())
               {
                   txtItemSsd = checkSsd.getText().toString();
                   txtExtraItem = txtItemSsd;
                   totalAmount = totalAmount + 60;
               }
               //Checking if only printer is selected and adding the ssd amount to the final cost
               else if(checkPrinter.isChecked())
               {
                   txtItemPrinter =  checkPrinter.getText().toString();
                   txtExtraItem = txtItemPrinter;
                   totalAmount = totalAmount + 100;
               }
               else
               {
                   txtExtraItem = null;
               }

               //Checking if peripheral cooling mat is selected and adding its price to the total cost
               if(rbCoolingMat.isChecked()== true)
               {
                   totalAmount = totalAmount + 33;
               }
               //Checking if peripheral USB is selected and adding its price to the total cost
               else if(rbUsb.isChecked()== true)
               {
                   totalAmount = totalAmount + 60;
               }
               //Checking if peripheral laptop stand is selected and adding its price to the total cost
               else if(rbLaptopStand.isChecked()== true)
               {
                   totalAmount = totalAmount + 139;
               }

               //Checking if peripheral webcam is selected and adding its price to the total cost
               if(rbWebcam.isChecked()== true)
               {
                   totalAmount = totalAmount + 95;
               }
               //Checking if peripheral hard drive is selected and adding its price to the total cost
               else if(rbHardDrive.isChecked()== true)
               {
                   totalAmount = totalAmount + 64;
               }
               //Checking if no peripheral is selected and adding its price to the total cost
               else if(rbNone.isChecked()== true)
               {
                    totalAmount = totalAmount + 0;
               }

               //Calculating the tax(13%) on the total amount
               taxAmount = totalAmount * 0.13;

               //Calculating the final amount after adding the tax in it
               totalAmount = totalAmount + taxAmount;

               //Displaying the Invoice including all the details entered by the user and showing the total cost to the user
               txtInvoice.setText("Customer: " + name + "\n" + "Province: " + txtProvince + "\n" +
                       "Date of Purchase: " + date + "\n" +"Computer: " + typeOfComputer + "\n" + "Brand: " + txtBrands
                        + "\n" + "Additional: " + txtExtraItem + "\n" + "Added Peripherals: " + txtExtraPeripheral +
                        "\n" + "Cost: $" + totalAmount);

           }
       });
    }
}

