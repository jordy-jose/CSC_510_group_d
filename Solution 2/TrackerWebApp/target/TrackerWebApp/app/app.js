Ext.application({
    name: 'MyApp',
    appFolder: 'app',
    autoCreateViewport: true,
    controllers: ['AuthenticationController'],
    launch: function() {
     console.log("Launching MyApp");
    }
});