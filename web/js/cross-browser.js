/**
 * cross-browser functions for support IE:
 * getWindowScroll(): return object with properties 'top','left'
 * getWindowInnerSize(): return object with properties 'width','height'
 * getOffset(): return object with properties 'top','left'
 */
function getWindowScroll(){
    if(typeof pageYOffset!= 'undefined' && typeof pageXOffset!= 'undefined'){
        //most browsers except IE before #9
        return {top: pageYOffset, left: pageXOffset};
    }
    else{
        var B = document.body; //IE 'quirks'
        var D = document.documentElement; //IE with doctype
        D= (D.clientHeight)? D: B;
        return {top: D.scrollTop, left: D.scrollLeft};
    }
}

function getWindowInnerSize(){
    if(window.innerWidth != undefined){
        return {width: window.innerWidth, height: window.innerHeight};
    }
    else{
        var B = document.body,
            D = document.documentElement;
        return {width: Math.max(D.clientWidth, B.clientWidth),
            height: Math.max(D.clientHeight, B.clientHeight)};
    }
}

function getOffset(obj) {
    var ol = ot = 0;
    if (obj.offsetParent) {
        do {
            ol += obj.offsetLeft;
            ot += obj.offsetTop;
        }while (obj = obj.offsetParent);
    }
    return {left : ol, top : ot};
}

function stopEventPropagation(event){
    var ev = event || window.event || getEvent(arguments.callee.caller);
    if(ev){
        if(ev.originalEvent) ev = ev.originalEvent;
        ev.stopPropagation ? ev.stopPropagation() : ( ev.cancelBubble = true );
    }
}

function setCaretAtEnd($input){
    var input, valLen;
    if(!$input || $input.length === 0){
        var context = $input.context;
        if(context && context.tagName.toUpperCase() === 'INPUT' && context.type.toLowerCase() === 'text'){
            input = context;
        }else {
            return;
        }
    }else {
        input = $input[0];
    }

    valLen = input.value.length;
    // For IE Only
    if (document.selection) {
        input.focus();
        // Use IE Ranges
        var oSel = document.selection.createRange();
        // Reset position to 0 & then set at end
        oSel.moveStart('character', -valLen);
        oSel.moveStart('character', valLen);
        oSel.moveEnd('character', 0);
        oSel.select();
    }
    else if (input.selectionStart || input.selectionStart == '0') {
        // Firefox/Chrome
        input.selectionStart = valLen;
        input.selectionEnd = valLen;
        input.focus();
    }
}

function isMSIE() {
    var ua = navigator.userAgent.toLowerCase();
    return (ua.indexOf("msie") >= 0) || document.documentMode > 0;
}

function getMSIEVersion(){
    var rv = -1; // Return value assumes failure.
    if (isMSIE()){
        var ua = navigator.userAgent;
        var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
        if (re.exec(ua) != null)
            rv = parseFloat( RegExp.$1 );
        if(rv > document.documentMode){
            rv = document.documentMode;
        }
        if(rv == -1) rv = document.documentMode;
    }
    return rv;
}

function isSafari() {
    var userAgent = navigator.userAgent;
    return /Safari\//.test(userAgent) && !/(Chrome\/|Edge\/|Android\s)/.test(userAgent);
}

function isIOS(){
    return /\b(iPad|iPhone|iPod)(?=;)/.test(navigator.userAgent);
}

function isFirefox(){
   return typeof InstallTrigger !== 'undefined';
}

function getEvent(caller){
    var e;
    if(!caller) return null;
    e = caller.arguments[0];
    try {
        if (e && (e instanceof Event || e instanceof jQuery.Event || e.type === 'click')) {
            return e;
        } else {
            return getEvent(caller.caller);
        }
    } catch(ex) {
        return e;
    }
}

function getRelatedTextInput(args,nullable){
    var evt = window.event || getEvent(args.callee.caller) || {},
        target = evt.target || evt.srcElement;

    if(!target && !nullable) throw new Error('Could not get related text input');

    return target;
}
// For supporting IE8 and less
// Production steps of ECMA-262, Edition 5, 15.4.4.14
// Reference: http://es5.github.io/#x15.4.4.14
if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function(searchElement, fromIndex) {

        var k;

        // 1. Let o be the result of calling ToObject passing
        //    the this value as the argument.
        if (this == null) {
            throw new TypeError('"this" is null or not defined');
        }

        var o = Object(this);

        // 2. Let lenValue be the result of calling the Get
        //    internal method of o with the argument "length".
        // 3. Let len be ToUint32(lenValue).
        var len = o.length >>> 0;

        // 4. If len is 0, return -1.
        if (len === 0) {
            return -1;
        }

        // 5. If argument fromIndex was passed let n be
        //    ToInteger(fromIndex); else let n be 0.
        var n = +fromIndex || 0;

        if (Math.abs(n) === Infinity) {
            n = 0;
        }

        // 6. If n >= len, return -1.
        if (n >= len) {
            return -1;
        }

        // 7. If n >= 0, then Let k be n.
        // 8. Else, n<0, Let k be len - abs(n).
        //    If k is less than 0, then let k be 0.
        k = Math.max(n >= 0 ? n : len - Math.abs(n), 0);

        // 9. Repeat, while k < len
        while (k < len) {
            // a. Let Pk be ToString(k).
            //   This is implicit for LHS operands of the in operator
            // b. Let kPresent be the result of calling the
            //    HasProperty internal method of o with argument Pk.
            //   This step can be combined with c
            // c. If kPresent is true, then
            //    i.  Let elementK be the result of calling the Get
            //        internal method of o with the argument ToString(k).
            //   ii.  Let same be the result of applying the
            //        Strict Equality Comparison Algorithm to
            //        searchElement and elementK.
            //  iii.  If same is true, return k.
            if (k in o && o[k] === searchElement) {
                return k;
            }
            k++;
        }
        return -1;
    };
}

// For supporting IE8 and less
if(typeof String.prototype.trim !== 'function') {
    String.prototype.trim = function() {
        return this.replace(/^\s+|\s+$/g, '');
    }
}

var log = (function(){

    var print = function(level,msg){
        if(window.console != null && window.console[level] != null){
            window.console[level](msg);
        }
    };

    return {
        print: function(msg){
            print('log',msg);
        },
        info: function(msg){
            print('info',msg);
        },
        warn: function(msg){
            print('warn',msg);
        },
        debug: function(msg){
            print('debug',msg);
        },
        err: function(msg){
            print('error',msg);
        }
    }
})();