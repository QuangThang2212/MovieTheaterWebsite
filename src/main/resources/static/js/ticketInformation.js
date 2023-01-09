

// history.pushState(null, document.URL, location.href);
// window.addEventListener('popstate', function () {
//     history.pushState(null, document.URL, location.href);
// });
window.history.pushState(null, null, window.location.href);
window.onpopstate = function () {
    window.history.go(1);
};
var myHistory = [];
window.history.pushState(myHistory, null, window.location.href);
window.history.replaceState(myHistory, null, window.location.href);
if (myHistory.length > 0) {
    var pg = myHistory.pop();
    window.history.pushState(myHistory, null, window.location.href);
    //Load page data for "pg".
}