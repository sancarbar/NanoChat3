package com.gdg.nanochat.ui.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.firebase.client.Firebase;
import com.gdg.nanochat.R;
import com.gdg.nanochat.model.ChatMessage;
import com.gdg.nanochat.ui.adapter.FirebaseListAdapter;

import java.util.HashMap;
import java.util.Map;

public class MainActivity
    extends ListActivity
{

    private Firebase firebaseRef;

    private EditText messageEditText;

    private FirebaseListAdapter listAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        messageEditText = (EditText) findViewById( R.id.message_text );

        //Allows Firebase client to keep its context
        Firebase.setAndroidContext( this );

        firebaseRef = new Firebase( "https://nanochat-gdg.firebaseio.com/" );

        listAdapter =
            new FirebaseListAdapter<ChatMessage>( firebaseRef, ChatMessage.class, R.layout.message_layout, this )
            {
                @Override
                protected void populateView( View v, ChatMessage model )
                {
                    ( (TextView) v.findViewById( R.id.username_text_view ) ).setText( model.getName() );
                    ( (TextView) v.findViewById( R.id.message_text_view ) ).setText( model.getMessage() );
                }
            };

        setListAdapter( listAdapter );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if ( id == R.id.action_settings )
        {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    public void onSendButtonClick( View view )
    {
        String message = messageEditText.getText().toString();
        firebaseRef.push().setValue( new ChatMessage( "puf", message ) );
        messageEditText.setText( "" );
    }
}
