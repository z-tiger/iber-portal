var myApp = new Framework7();
var $$ = Dom7;
var mainView = myApp.addView('.view-main', {
});


myApp.onPageInit('enterpriseCoupon', function (page) {
	alert();
	myApp.alert('Notification closed');
    $$('.button').on('click', function () {
    	myApp.addNotification({
            message: 'Close me to see Alert',
            button: {
                text: 'Close Me',
                color: 'lightgreen'
            },
            onClose: function () {
                myApp.alert('Notification closed');
            }
        });
    });
});

