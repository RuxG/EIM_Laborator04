package com.example.contactsmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Logger;


public class ContactsManagerActivity extends Activity {

    Button saveButton;
    Button cancelButton;
    Button showDetailsButton;
    EditText nameText;
    EditText phoneText;
    EditText emailText;
    EditText addressText;
    EditText jobTitleText;
    EditText companyText;
    EditText websiteText;
    EditText imText;

    LinearLayout additionalDetails;
    ButtonListener buttonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        initViews();
    }

    protected void initViews() {
        buttonListener = new ButtonListener();

        saveButton = (Button) findViewById(R.id.save_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        showDetailsButton = (Button) findViewById(R.id.additional_fields_button);
        additionalDetails = (LinearLayout) findViewById(R.id.additional_details_layout);

        saveButton.setOnClickListener(buttonListener);
        cancelButton.setOnClickListener(buttonListener);
        showDetailsButton.setOnClickListener(buttonListener);

        nameText = (EditText) findViewById(R.id.name_edit_text);
        phoneText = (EditText) findViewById(R.id.phone_number_edit_text);
        emailText = (EditText) findViewById(R.id.email_edit_text);
        addressText = (EditText) findViewById(R.id.address_edit_text);
        jobTitleText = (EditText) findViewById(R.id.job_title_edit_text);
        companyText = (EditText) findViewById(R.id.company_edit_text);

        websiteText = (EditText) findViewById(R.id.website_edit_text);
        imText = (EditText) findViewById(R.id.IM_edit_text);

    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == showDetailsButton.getId()) {
                if (additionalDetails.getVisibility() == View.VISIBLE) {
                    additionalDetails.setVisibility(View.GONE);
                    ((Button) view).setText(Constants.SHOW_ADDITIONAL_DETAILS);
                } else {
                    additionalDetails.setVisibility(View.VISIBLE);
                    ((Button) view).setText(Constants.HIDE_ADDITIONAL_DETAILS);
                }
            } else if (view.getId() == saveButton.getId()) {

                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                String name = nameText.getText().toString();
                intent.putExtra(ContactsContract.Intents.Insert.NAME, name);

                String phone = phoneText.getText().toString();
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);

                String email = emailText.getText().toString();
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);

                String address = addressText.getText().toString();
                intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);

                String jobTitle = jobTitleText.getText().toString();
                intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);

                String company = companyText.getText().toString();
                intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);

                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();

                String website = websiteText.getText().toString();
                ContentValues websiteRow = new ContentValues();
                websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                contactData.add(websiteRow);

                String im = imText.getText().toString();
                ContentValues imRow = new ContentValues();
                imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                contactData.add(imRow);

                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivity(intent);

            } else if (view.getId() == cancelButton.getId()) {
                finish();
            }
        }
    }
}