var menuLevels = {
    "level0":{"open":"open-book", "close":"close-book"},
    "level1":{"open":"open-book1", "close":"close-book1"},
    "level2":{"open":"open", "close":"close"},
    "level3":{"open":"open1", "close":"close1"},
    "level4":{"open":"page", "close":"page"},
    "level5":{"open":"page1", "close":"page1"}
};
$(document).ready(function() {
    $('.left-menu').delegate('.left-menu-img', 'click', function() {
        var menuLevel = menuLevels[$(this).attr("level")];
        var opened = $(this).attr("opened");
        var submenu = $(this).siblings('div');
        if (opened === 'open') {
            submenu.hide();
            opened = 'close';
        } else {
            submenu.show();
            opened = 'open';
        }
        $(this).attr('opened', opened);
        $(this).parent().removeClass().addClass('left-menu-item').addClass(menuLevel[opened]);
    });
});
/** поиск элемента */
function getNodeFrame(nodeId, doc) {
  if (doc.getElementById) {
      return doc.getElementById(nodeId);
  } else if(doc.all && doc.all(nodeId)) {
      return doc.all(nodeId);
  } else if(document.layers && document.layers[nodeId]) {
      return document.layers[nodeId];
  } else {
      return false;
  }
}

/** поиск элемента */
function getNode(nodeId) {
  var doc = document;
  var win = window;
  var node;
  var i = 0;
  if (win.parent) {
      win = win.parent;
      doc = win.document;
  }
  node = getNodeFrame(nodeId, doc);
  if (node) return node;
  for(i = 0; i < doc.frames.length; i++) {
    node = getNodeFrame(nodeId, doc.frames[i].document);
    if (node) return node;
  }
}

var currentMenu = null;
var currentTimer = null;

/* Показываем подменю гавного меню. */
function selectMenu(a, name) {

    var elem = a;

    var offy = elem.offsetTop + elem.offsetHeight;
    var offx = elem.offsetLeft;// + elem.offsetWidth;

    while (elem.offsetParent != null)
    {
        elem = elem.offsetParent;
        offy += elem.offsetTop;
        offx += elem.offsetLeft;
        if (elem.tagName == 'body') break;
    }

    if(currentTimer) clearTimeout(currentTimer);
    currentTimer=null;
    var curMenu;
    if(currentMenu) {
        curMenu = getNode(currentMenu);
        curMenu.style.display='none';
    }
    currentMenu = name;
    curMenu = getNode(currentMenu);
    if (curMenu) {
        curMenu.style.top = offy + "px";
        curMenu.style.left = offx + "px";
        curMenu.style.display = 'block';
    }

    //alert(getNode(currentMenu).style.top);
}

/**************************/
function overMenu() {
  if(currentTimer) clearTimeout(currentTimer);
}

/**************************/
function unselectMenu() {
  currentTimer = setTimeout('closeMenu()',100);
}

/**************************/
function closeMenu() {
  if(currentMenu) {
    getNode(currentMenu).style.display='none';
  }
  if(currentTimer) clearTimeout(currentTimer);
  currentTimer=null;
  currentMenu=null;
}

/**************************/
function relPosX(which) {

    dom = (document.getElementById) ? true : false;
    var elem = (dom)? document.getElementById(which) : document.all[which];
    var pos = elem.offsetLeft;
    while (elem.offsetParent != null) {
      elem = elem.offsetParent;
      pos += elem.offsetLeft;
      if (elem.tagName == 'body') break;
    } return pos;

}

/**************************/
function relPosY(which) {

    dom = (document.getElementById) ? true : false;
    var elem = (dom)? document.getElementById(which) : document.all[which];
    var pos = elem.offsetTop;
    while (elem.offsetParent != null) {
      elem = elem.offsetParent;
      pos += elem.offsetTop;
      if (elem.tagName == 'body') break;
    } return pos;

}

//дополняет нулем часы либо минуты
function num_fmt(p)
{
    var z = String(parseInt(p));
    //alert(z.length);
    if (z.length==1) return "0"+z;
    return z;

}

/** загружаем основной документ */
function LoadUrl(mainUrl, menuUrl, title) {
   if (mainUrl) {
      var mainNode = getNode("Doc_main");
      if (mainNode) {
         mainNode.src = mainUrl;
      }
   }
   if (menuUrl) {
     var menuNode = getNode("Menu_main");
     if (menuNode) {
        menuNode.src = menuUrl;
     }
   }
   SetMainTitle(title);
}

/** загружаем главное меню */
function SetMainTitle(text) {
   var title = getNode("Title_main");
   if (text && title) {
      title.firstChild.data = text;
   }
   closeMenu();
}
