Ext.define('MyApp.view.AuthenticationForm',{
 extend: 'Ext.form.Panel',
 alias: 'widget.authBox',
    title: 'Authentication Form',
    width: 250,
    bodyPadding:5,
    fieldDefaults: {
        msgTarget: 'side',
        labelWidth: 75
    },
    defaultType: 'textfield',
    url:'authenticate.form',
    items:[{
        fieldLabel: 'Login',
        name: 'login',
        allowBlank:false
    },{
        fieldLabel: 'Password',
        name: 'password'
    }],
    buttons: [{
        text: 'Submit',
        action:'auth'
    },{
        text: 'Reset'
    }]
 }
);