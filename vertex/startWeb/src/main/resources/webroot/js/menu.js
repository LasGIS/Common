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

/*
function getElement(name) {
  if(document.all) {
    return document.all[name];
  } else {
    return document.getElementById(name);
  }
}
*/

var currentMenu = null;
var currentTimer = null;
var ImageType = [
  "\n\r<img border='0' src='" + ImagePrefix + "MenuOpen1.png' hspace='5'",
  "\n\r<img border='0' src='" + ImagePrefix + "MenuClose1.png' hspace='5'",
  "\n\r<img border='0' src='" + ImagePrefix + "MenuOpenBook.png' hspace='5'",
  "\n\r<img border='0' src='" + ImagePrefix + "MenuCloseBook.png' hspace='5'",
  "\n\r<img border='0' src='" + ImagePrefix + "MenuOpenBook1.png' hspace='5'",
  "\n\r<img border='0' src='" + ImagePrefix + "MenuCloseBook1.png' hspace='5'",
  "\n\r<img border='0' src='" + ImagePrefix + "MenuPage1.png' hspace='5'", ""];

/**************************/
function selectMenu(a, name) {

    var elem = a;

    //dom = (document.getElementById)? true : false;
    //var elem = (dom)? document.getElementById(which) : document.all[which];

    var offy = elem.offsetTop + elem.offsetHeight;
    var offx = elem.offsetLeft;// + elem.offsetWidth;

    while (elem.offsetParent != null)
    {
        elem = elem.offsetParent;
        offy += elem.offsetTop;
        offx += elem.offsetLeft;
        if (elem.tagName == 'BODY') break;
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
      if (elem.tagName == 'BODY') break;
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
      if (elem.tagName == 'BODY') break;
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

/** загружаем подменю меню */
function LoadMenu(subm0, subm1, subm2, subm3) {
  var iSub0 = 0,iSub1 = 0, iSub2 = 0, iSub3 = 0;
  var isView0 = true, isView1 = true, isView2 = true, isView3 = true;

  var str="<table class='SubMenu' width='100%' border='0' cellspacing='0' cellpadding='0'>";
  for (i = 0; i < StructMenu.length; i+=4) {
    switch (StructMenu[i]) {
    case 0:
      iSub0++; iSub1 = 0; iSub2 = 0; iSub3 = 0;
      if ((subm0 == iSub0) && (subm1 < 0) && (subm2 < 0) && (subm3 < 0)) {
        StructMenu[i + 1] = !StructMenu[i + 1];
      }
      isView0 = StructMenu[i + 1];
      str += "<tr><td>";
      if (isView0) {
        str += ImageType[0];
      } else {
        str += ImageType[1];
      }
      str += " language='javascript' onclick='return LoadMenu("
        + iSub0 + ",-1,-1,-1)' >";
      str += "</td><td>";
      if (StructMenu[i + 2].length > 0) {
        str += "<a href='" + StructMenu[i + 2] + "' target='target_doc'>"
          + StructMenu[i + 3]
          + "</a>";
      } else {
        str += "<strong>" + StructMenu[i + 3] + "</strong>";
      }
      str += "</td></tr>\n\r";
      break;
    case 1:
      iSub1++; iSub2 = 0; iSub3 = 0;
      if ((subm0 == iSub0) && (subm1 == iSub1) && (subm2 < 0) && (subm3 < 0)) {
        StructMenu[i + 1] = !StructMenu[i + 1];
      }
      isView1 = StructMenu[i + 1];
      if (isView0) {
        str += "<tr><td>&nbsp;";
        if (isView1) {
          str += ImageType[2];
        } else {
          str += ImageType[3];
        }
        str += " language='javascript' onclick='return LoadMenu("
          + iSub0 + "," + iSub1 + ",-1,-1)' >";
        str += "</td><td>";
        if (StructMenu[i + 2].length > 0) {
          str += "<a href='" + StructMenu[i + 2] + "' target='target_doc'>"
            + StructMenu[i + 3]
            + "</a>";
        } else {
          str += "<strong>" + StructMenu[i + 3] + "</strong>";
        }
        str += "</td></tr>\n\r";
      }
      break;
    case 2:
      iSub2++; iSub3 = 0;
      if ((subm0 == iSub0) && (subm1 == iSub1) && (subm2 == iSub2) && (subm3 < 0)) {
        StructMenu[i + 1] = !StructMenu[i + 1];
      }
      isView2 = StructMenu[i + 1];
      if (isView0 && isView1) {
        str += "<tr><td>&nbsp;&nbsp;";
        if (isView2) {
          str += ImageType[4];
        } else {
          str += ImageType[5];
        }
        str += " language='javascript' onclick='return LoadMenu("
          + iSub0 + "," + iSub1 + "," + iSub2 + ",-1)' >";
        str += "</td><td>";
        if (StructMenu[i + 2].length > 0) {
          str += "<a href='" + StructMenu[i + 2] + "' target='target_doc'>"
            + StructMenu[i + 3]
            + "</a>";
        } else {
          str += "<strong>" + StructMenu[i + 3] + "</strong>";
        }
        str += "</td></tr>\n\r";
      }
      break;
    case 3:
      iSub3++;
      if ((subm0 == iSub0) && (subm1 == iSub1) && (subm2 == iSub2) && (subm3 == iSub3)) {
        StructMenu[i + 1] = !StructMenu[i + 1];
      }
      isView3 = StructMenu[i + 1];
      if (isView0 && isView1 && isView2) {
        str += "<tr><td>&nbsp;&nbsp;&nbsp;"
          + ImageType[6]
          + " language='javascript' onclick='return LoadMenu("
          + iSub0 + "," + iSub1 + "," + iSub2 + "," + iSub3 + ")' >"
          + "</td><td>";
        if (StructMenu[i + 2].length > 0) {
          str += "<a href='" + StructMenu[i + 2] + "' target='target_doc'>"
            + StructMenu[i + 3]
            + "</a>";
        } else {
          str += "<strong>" + StructMenu[i + 3] + "</strong>";
        }
        str += "</td></tr>\n\r";
      }
      break;
    }
  }
  str += "</table>";
  var m_Menu = document.getElementById("MyMenu");
  m_Menu.innerHTML = str;
}
