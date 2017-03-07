require.config({
    baseUrl: "/static",

    paths: {
        "async"         : "js/vendor/requirejs/async",
        "console"       : "js/plugins",
        "jquery"        : "js/vendor/jquery-3.1.1.min",
        "bootstrap"     : "js/vendor/bootstrap/js/bootstrap.min",
        "clipboard"     : "js/vendor/clipboard/clipboard.min",
    
        "common"		: "/js/common",
    },
    

    shim: {
        "bootstrap": {
            deps: ["jquery"]
        }
    },

    deps: ["console"]
});
