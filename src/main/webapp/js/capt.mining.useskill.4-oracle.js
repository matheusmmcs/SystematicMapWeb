/*!
 * Configuration:

 <script type="text/javascript" src="http://useskill.com/jscripts/useskill/capt.mining.useskill.nojquery.js"></script>
 <script type="text/javascript">
 USER = {
 username: "username-"+new Date().getTime(),
 role: "role-"+new Date().getTime()
 };

 useskill_capt_onthefly({
 url: "http://easii.ufpi.br/useskill-capture/actions/create",
 waitdomready: false, //se for verdadeiro, aguarda o evento DOMContentLoaded ser disparado, caso contrario ja executa a captura
 sendactions: false, //enviar acoes para o servidor
 debug: true, //apresentar dados no console

 onthefly: true, //para scripts inseridos via onthefly
 captureback: false, //captura de eventos de voltar -> con
 capturehashchange: false, //capturar eventos de mudanca de hash
 jheat: false, //para sistemas baseados no jheat
 plugin: false, //apenas para script via plugin do chrome

 client: "Test", //nome do cliente
 version: 1, //versao do script de captura
 username: function() { return USER.username; }, //pode ser funcao ou uma string
 role: function() { return USER.role; } //pode ser funcao ou uma string
 });
 </script>

 */

/*!
 * Back Button Detection Object V 1.0.1
 * http://www.brookebryan.com/
 *
 * Copyright 2010, Brooke Bryan
 *
 * Date: Thu 27 Jan 2011 13:37 GMT
 */
try {
	var bajb_backdetect = {
		Version : '1.0.0',
		Description : 'Back Button Detection',
		Browser : {
			IE : !!(window.attachEvent && !window.opera),
			Safari : navigator.userAgent.indexOf('Apple') > -1,
			Opera : !!window.opera
		},
		FrameLoaded : 0,
		FrameTry : 0,
		FrameTimeout : null,
		OnBack : function() {
			console.log('Back Button Clicked')
		},
		BAJBFrame : function() {
			var BAJBOnBack = document.getElementById('BAJBOnBack');
			if (bajb_backdetect.FrameLoaded > 1) {
				if (bajb_backdetect.FrameLoaded == 2) {
					bajb_backdetect.OnBack();
					history.back()
				}
			}
			bajb_backdetect.FrameLoaded++;
			if (bajb_backdetect.FrameLoaded == 1) {
				if (bajb_backdetect.Browser.IE) {
					bajb_backdetect.SetupFrames()
				} else {
					bajb_backdetect.FrameTimeout = setTimeout(
							"bajb_backdetect.SetupFrames();", 700)
				}
			}
		},
		SetupFrames : function() {
			clearTimeout(bajb_backdetect.FrameTimeout);
			var BBiFrame = document.getElementById('BAJBOnBack');
			var checkVar = BBiFrame.src.substr(-11, 11);
			if (bajb_backdetect.FrameLoaded == 1 && checkVar != "HistoryLoad") {
				BBiFrame.src = "blank.html?HistoryLoad"
			} else {
				if (bajb_backdetect.FrameTry < 2 && checkVar != "HistoryLoad") {
					bajb_backdetect.FrameTry++;
					bajb_backdetect.FrameTimeout = setTimeout(
							"bajb_backdetect.SetupFrames();", 700)
				}
			}
		},
		SafariHash : 'false',
		Safari : function() {
			if (bajb_backdetect.SafariHash == 'false') {
				if (window.location.hash == '#b') {
					bajb_backdetect.SafariHash = 'true'
				} else {
					window.location.hash = '#b'
				}
				setTimeout("bajb_backdetect.Safari();", 100)
			} else if (bajb_backdetect.SafariHash == 'true') {
				if (window.location.hash == '') {
					bajb_backdetect.SafariHash = 'back';
					bajb_backdetect.OnBack();
					history.back()
				} else {
					setTimeout("bajb_backdetect.Safari();", 100)
				}
			}
		},
		Initialise : function() {
			if (bajb_backdetect.Browser.Safari) {
				setTimeout("bajb_backdetect.Safari();", 600)
			} else {
				document
						.write('<iframe src="blank.html" style="display:none;" id="BAJBOnBack" onunload="alert(\'de\')" onload="bajb_backdetect.BAJBFrame();"></iframe>')
			}
		}
	};

	/*
	 * Verificar alteracoes no hash da URL.
	 */
	(function() {
		if ('onhashchange' in window) {
			if (window.addEventListener) {
				window.addHashChange = function(func, before) {
					window.addEventListener('hashchange', func, before);
				};
				window.removeHashChange = function(func) {
					window.removeEventListener('hashchange', func);
				};
				return;
			} else if (window.attachEvent) {
				window.addHashChange = function(func) {
					window.attachEvent('onhashchange', func);
				};
				window.removeHashChange = function(func) {
					window.detachEvent('onhashchange', func);
				};
				return;
			}
		}
		var hashChangeFuncs = [];
		var oldHref = location.href;
		window.addHashChange = function(func, before) {
			if (typeof func === 'function') {
				hashChangeFuncs[before ? 'unshift' : 'push'](func);
			}
		};
		window.removeHashChange = function(func) {
			for (var i = hashChangeFuncs.length - 1; i >= 0; i--) {
				if (hashChangeFuncs[i] === func) {
					hashChangeFuncs.splice(i, 1);
				}
			}
		};

		if (window.hashChangeInterval) {
			clearInterval(window.hashChangeInterval);
		}
		window.hashChangeInterval = setInterval(function() {
			var newHref = location.href;
			if (oldHref !== newHref) {
				var _oldHref = oldHref;
				oldHref = newHref;
				for (var i = 0; i < hashChangeFuncs.length; i++) {
					hashChangeFuncs[i].call(window, {
						'type' : 'hashchange',
						'newURL' : newHref,
						'oldURL' : _oldHref
					});
				}
			}
		}, 100);
	})();

	/*
	 * UseSkill capture component V 1.0.1 http://www.useskill.com/
	 * 
	 */

	function useskill_capt_onthefly(obj) {

		console.log('capt!');

		function doAjax(method, url, params, cbSuccess, cbError) {
			var xmlhttp = new XMLHttpRequest(), asynchronous = true, cache = false;

			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == XMLHttpRequest.DONE) {
					if (xmlhttp.status == 200) {
						if (cbSuccess && typeof (cbSuccess) === "function") {
							cbSuccess(xmlhttp.responseText);
						}
					} else {
						if (cbError && typeof (cbError) === "function") {
							cbError(xmlhttp.responseText);
						}
					}
				}
			}

			url = url
					+ (cache ? (((/\?/).test(url) ? "&" : "?") + (new Date())
							.getTime()) : '');
			url = url
					+ (method === 'GET' && params != null ? (((/\?/).test(url) ? "&"
							: "?") + params)
							: '');
			method = method.toUpperCase();

			xmlhttp.open(method, url, asynchronous);
			xmlhttp.setRequestHeader("Content-type",
					"application/json;charset=UTF-8");

			if (method === 'POST' && params != null) {
				xmlhttp.send(JSON.stringify(params));
			} else {
				xmlhttp.send();
			}
		}

		var DOM_READY = obj.waitdomready ? obj.waitdomready : false;

		function runCapt() {

			if (!obj) {
				obj = {};
			}

			// "http://easii.ufpi.br/useskill-capture/actions/create"
			// "http://localhost:3000/actions/create";
			// "http://96.126.116.159:3000/actions/create"

			// REQUIRED
			var URL = obj.url ? obj.url
					: "http://easii.ufpi.br/useskill-capture/actions/create";
			// ON THE FLY
			var CLIENT = obj.client ? obj.client : "";
			var VERSION = obj.version ? obj.version : 0;
			var USERNAME = obj.username ? obj.username : "";
			var ROLE = obj.role ? obj.role : "";
			// CONFIGURATIONS
			var ACTIONS_SIZE_LIMIT = obj.actionsizelimit ? obj.actionsizelimit
					: 200;
			var TIME_TO_SUBMIT = obj.timetosubmit ? obj.timetosubmit : 120;
			var SEND_MESSAGES = obj.sendactions ? obj.sendactions : false;
			var DEBUG = obj.debug ? obj.debug : false;
			var CONNECTED_PLUGIN = obj.plugin ? obj.plugin : false;
			var CONNECTED_ON_THE_FLY = obj.onthefly ? obj.onthefly : false;
			var CONNECTED_ON_JHEAT = obj.jheat ? obj.jheat : false;
			var CAPTURE_BACK = obj.captureback ? obj.captureback : false;
			var CAPTURE_HASH_CHANGE = obj.capturehashchange ? obj.capturehashchange
					: false;
			// ORACLA CONFIGURATIONS
			var ORACLE_ELEMENTS = obj.oracleElements ? obj.oracleElements : [];

			// VARIABLES
			var TIME_SUBMIT = 1000 * TIME_TO_SUBMIT, ultimaAcao = null, sending = false, LOCALSTORAGE_VAR = "";

			if (CONNECTED_PLUGIN) {
				console.log("#Plugin - UseSkill Capt Running...");
				LOCALSTORAGE_VAR = "useskill_actions_plugin";
			} else {
				console.log("UseSkill Capt Running...");
				LOCALSTORAGE_VAR = "useskill_actions";
			}
			debug(obj);

			// CLEAR EXCESSIVE DATA
			if (getAcoes().length > ACTIONS_SIZE_LIMIT) {
				console.log('Too large... clearing actions');
				var x = localStorage.getItem(LOCALSTORAGE_VAR + "_garbage");
				x = x == null ? [] : parseJSON(x);
				x.push({
					date : new Date().getTime(),
					actionsSize : getAcoes().length,
					actions : getAcoes()
				});
				localStorage.setItem(LOCALSTORAGE_VAR + "_garbage",
						stringfyJSON(x));
				clearStorage();
			}

			// MOBILE VERIFICATION
			var isMobileType = {
				Android : function() {
					return /Android/i.test(navigator.userAgent);
				},
				BlackBerry : function() {
					return /BlackBerry/i.test(navigator.userAgent);
				},
				iOS : function() {
					return /iPhone|iPad|iPod/i.test(navigator.userAgent);
				},
				Windows : function() {
					return /IEMobile/i.test(navigator.userAgent);
				},
				any : function() {
					return (isMobileType.Android() || isMobileType.BlackBerry()
							|| isMobileType.iOS() || isMobileType.Windows());
				}
			};
			function isMobile() {
				return isMobileType.any();
			}

			// DEBUG
			function debug() {
				if (DEBUG) {
					if (CONNECTED_PLUGIN) {
						console.log('#Plugin', arguments);
					} else {
						console.log(arguments);
					}
				}
			}
			debug("All Actions in Buffer: ", getAcoes());

			// ENVIO DE EVENTOS
			if (SEND_MESSAGES) {
				sendAcoes();
				var interval = setInterval(function() {
					sendAcoes();
				}, TIME_SUBMIT);

				// window.onbeforeunload = function(e) {
				// sendAcoes();
				// };
			}

			function sendAcoes() {
				debug("SendActions", SEND_MESSAGES);
				if (SEND_MESSAGES) {
					var acoesString = getAcoesString();
					if (acoesString && !sending) {
						sending = true;
						doAjax(
								"POST",
								URL,
								{
									acoes : acoesString
								},
								function(result) {
									debug(
											"Success in Send Actions to Server: ",
											result);
									clearStorage();
									sending = false;
								}, function(data) {
									debug("Error in Send Actions to Server: ",
											data);
									sending = false;
								});
					}
				}
			}
			// window.onpopstate = function(event) {
			// console.log("MUDOU HASH: " + JSON.stringify(event.state));
			// };

			// ENUM DE TIPOS DE ACOES
			var actionCapt = {
				CLICK : "click",
				DBLCLICK : "dblclick",
				FOCUSOUT : "focusout",
				MOUSEOVER : "mouseover",

				TAP : "tap",
				DBLTAP : "dbltap",
				SWIPEUP : "swipeup",
				SWIPEDOWN : "swipedown",
				SWIPELEFT : "swipeleft",
				SWIPERIGHT : "swiperight",
				ORIENTARION : "orientationchage",

				BROWSER_BACK : "back",
				BROWSER_HASH_CHANGE : "hashchange",
				BROWSER_FORM_SUBMIT : "form_submit",
				BROWSER_ONLOAD : "onload",
				BROWSER_RELOAD : "reload",
				BROWSER_CLOSE : "close",

				ROTEIRO : "roteiro",
				CONCLUIR : "concluir",
				COMENTARIO : "comentario",
				PULAR : "pular"
			};

			function Action(action, time, url, content, tag, tagIndex, id,
					classe, name, xPath, posX, posY, viewportX, viewportY,
					useragent) {
				this.sActionType = action;
				this.sTime = time;
				this.sUrl = url;
				this.sContent = String(content).replace(/&/g, '&amp;').replace(
						/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g,
						'&quot;');
				this.sContent = this.sContent.substr(0, 200);
				this.sTag = tag;
				this.sTagIndex = tagIndex;
				this.sId = id;
				this.sClass = classe;
				this.sName = name;
				this.sXPath = xPath;
				this.sPosX = posX;
				this.sPosY = posY;
				this.sViewportX = viewportX;
				this.sViewportY = viewportY;
				this.sUserAgent = useragent;

				this.sRealTime = new Date().getTime();
				this.sTimezoneOffset = new Date().getTimezoneOffset();

				if (CONNECTED_ON_THE_FLY) {
					this.sUsername = typeof (USERNAME) === "function" ? USERNAME()
							: USERNAME;
					this.sRole = typeof (ROLE) === "function" ? ROLE() : ROLE;

					this.sClient = CLIENT;
					this.sVersion = VERSION;
				}

				if (CONNECTED_ON_JHEAT) {
					this.sJhm = getJhmName();
					this.sActionJhm = getActionJhm();
					this.sSectionJhm = getSectionJhm();
					this.sStepJhm = getStepJhm();
				}

				if (ORACLE_ELEMENTS.length) {
					this.sOracleElements = getOracleElementsCount();
				}

				this.sOracleVisibleElements = getOracleVisibleElements();
				this.sOracleUrl = this.sUrl;
			}

			// LISTENERS DE EVENTOS
			var lastMouseMove = 0, lastMouseX, lastMouseY, mouseOffSet = 5;
			if (isMobile()) {
				document.addEventListener('tap', function(e) {
					insertNewAcao(e, actionCapt.TAP);
				}, false);
				document.addEventListener('swipeup', function(e) {
					insertNewAcao(e, actionCapt.SWIPEUP);
				}, false);
				document.addEventListener('swipedown', function(e) {
					insertNewAcao(e, actionCapt.SWIPEDOWN);
				}, false);
				document.addEventListener('swipeleft', function(e) {
					insertNewAcao(e, actionCapt.SWIPELEFT);
				}, false);
				document.addEventListener('swiperight', function(e) {
					insertNewAcao(e, actionCapt.SWIPERIGHT);
				}, false);
				document.addEventListener('orientationchange', function(e) {
					insertNewAcao(null, actionCapt.ORIENTARION);
				}, false);
				document.addEventListener('dbltap', function(e) {
					insertNewAcao(e, actionCapt.DBLTAP);
				}, false);
			} else {
				document.addEventListener("click", function(e) {
					insertNewAcao(e, actionCapt.CLICK);
				}, false);
				document.addEventListener("dblclick", function(e) {
					insertNewAcao(e, actionCapt.DBLCLICK);
				}, false);
				document.addEventListener("mousemove", function(e) {
					if (e.timeStamp - lastMouseMove >= 2000) {
						if (e.pageX <= lastMouseX + mouseOffSet
								&& e.pageX >= lastMouseX - mouseOffSet
								&& e.pageY <= lastMouseY + mouseOffSet
								&& e.pageY >= lastMouseY - mouseOffSet) {
							insertNewAcao(e, actionCapt.MOUSEOVER);
						}
					}
					lastMouseMove = e.timeStamp;
					lastMouseX = e.pageX;
					lastMouseY = e.pageY;
				}, false);
			}

			document.addEventListener("focusout", function(e) {
				insertNewAcao(e, actionCapt.FOCUSOUT);
			}, false);
			document.addEventListener("submit", function(e) {
				insertNewAcao(e, actionCapt.BROWSER_FORM_SUBMIT);
			}, false);

			// capturar dados do jheat
			function getValueInputMeta(elem) {
				if (elem) {
					if (elem.length) {
						return elem[0].value;
					} else {
						return elem.value;
					}
				} else {
					return null;
				}
			}

			function getJhmName() {
				var val = getValueInputMeta(document
						.getElementsByName('flowName'));
				if (val != null && val != '') {
					return val;
				} else {
					return getRegexValue(
							/(reportName|flowName|className)\=([^\&|\#]*)/g,
							true);
				}
			}

			function getSectionJhm() {
				var val = getValueInputMeta(document
						.getElementsByName('sectionName'));
				if (val != null && val != '') {
					return val;
				} else {
					return getRegexValue(/(sectionName)\=([^\&|\#]*)/g, true);
				}
			}

			function getActionJhm() {
				var val = getValueInputMeta(document
						.getElementsByName('action'));
				if (val != null && val != '') {
					return val;
				} else {
					return getRegexValue(/(action)\=([^\&|\#]*)/g, true);
				}
			}

			function getStepJhm() {
				var val = getValueInputMeta(document.getElementsByName('step'));
				if (val != null && val != '') {
					return val;
				} else {
					return '';
				}
			}

			function getRegexValue(reg, fullSize) {
				var matches = reg.exec(window.location.href);
				// {type: matches[1], value: matches[2]} : {type:"", value: ""};
				return matches != null ? (fullSize ? matches[1] + "="
						+ matches[2] : matches[2]) : "";
			}

			// eventos extras, referentes ao carregamento da pagina e ao botao
			// voltar
			window.onload = function(e) {
				insertNewAcao(e, actionCapt.BROWSER_ONLOAD);
			}

			if (CAPTURE_BACK) {
				bajb_backdetect.Initialise();
				bajb_backdetect.OnBack = function(e) {
					insertNewAcao({
						target : document.getElementsByTagName("html"),
						pageX : 0,
						pageY : 0
					}, actionCapt.BROWSER_BACK);
				}
			}

			if (CAPTURE_HASH_CHANGE) {
				addHashChange(function(e) {
					insertNewAcao({
						target : document.getElementsByTagName("html"),
						pageX : 0,
						pageY : 0
					}, actionCapt.BROWSER_HASH_CHANGE);
					if (SEND_MESSAGES) {
						sendAcoes();
					}
				});
			}

			document.onkeydown = function(event) {
				if (typeof event != 'undefined') {
					// Ctrl+R ou F5
					if (event.keyCode == 116
							|| (event.ctrlKey && event.keyCode == 82)) {
						insertNewAcao(event, actionCapt.BROWSER_RELOAD);
					}
					// Ctrl+W ou ALT+F4
					if ((event.ctrlKey && event.keyCode == 87)
							|| (event.altKey && event.keyCode == 114)) {
						insertNewAcao(event, actionCapt.BROWSER_CLOSE);
					}
					if (event.ctrlKey && event.keyCode == 88) {
						// sendAcoes();
					}
				}
			};

			/* FUNcoES EXTRAS */
			function insertNewAcao(e, action) {
				var target = e.target, tagName;

				if (e != null) {
					target = e.target;
					tagName = target.tagName;
					if (CONNECTED_PLUGIN) {
						action = filterAction(e, target, action);
					}
				} else {
					e = {
						pageY : 0,
						pageX : 0
					};
					target = document.getElementsByTagName('HTML')[0];
					tagName = target.tagName;
				}

				if (action) {
					var conteudo = getContent(target, action);
					// filtros de captura de dados
					// focusout em campo vazio, nao preencheu nada
					if (!(action == actionCapt.FOCUSOUT && conteudo == "")) {
						var acao = new Action(action, new Date().getTime(),
								getUrl(), conteudo, target.tagName,
								getIndexElement(tagName, target), target.id,
								target.className, target.name,
								createXPathFromElement(e.target), e.pageX,
								e.pageY, window.innerWidth, window.innerHeight,
								(get_browser() + " " + get_browser_version()));
						debug("New Action: ", action, acao);
						// verificar se a acao eh semelhante com a acao passada
						// console.log(acao, acao.sXPath);
						// TODO: se for concluir, verificar se ja ha outro
						// concluir
						if (ultimaAcao) {
							var acoesRapidas = (action == actionCapt.CLICK);
							// se for acao permitida, nao houver acao anterir ou
							// se
							// for mais demorado que 10ms
							if (acoesRapidas
									|| !ultimaAcao.sTime
									|| acao.sActionType != ultimaAcao.sActionType
									|| (acao.sTime && acao.sTime
											- ultimaAcao.sTime > 10)) {
								ultimaAcao = acao;
								addAcao(acao);
							} else {
								ultimaAcao = acao;
								// console.log("Evitou repeticao:", ultimaAcao);
							}
						} else {
							// primeira acao
							ultimaAcao = acao;
							addAcao(acao);
						}
					}
				}
			}
			function getIndexElement(tag, el) {
				var arr = [].slice.call(document
						.getElementsByTagName(el.tagName));
				return arr.indexOf(el);
			}
			/**
			 * Receber o conteudo da acao de acordo com o elemento e a acao
			 */
			function getContent(target, action) {
				var val = null, action = action.toLowerCase();

				if (action == actionCapt.CLICK || action == actionCapt.DBLCLICK
						|| action == actionCapt.TAP
						|| action == actionCapt.DBLTAP
						|| action == actionCapt.SWIPEUP
						|| action == actionCapt.SWIPEDOWN
						|| action == actionCapt.SWIPELEFT
						|| action == actionCapt.SWIPERIGHT) {
					val = getTextByElement(target);
				} else if (action == actionCapt.ORIENTARION) {
					val = window.orientation;
				} else if (action == actionCapt.COMENTARIO) {
					val = document.getElementById('UScomentarioTexto').value;
				} else if (action == actionCapt.CLICK) {
					val = target.innerHTML; /* TODO: change to text */
				} else if (action == actionCapt.FOCUSOUT) {
					val = target.value;
				} else if (action == actionCapt.MOUSEOVER) {
					// receber o title caso seja de um no pai
					var count = 0, parent = target;
					while (val == "" || val == null) {
						count++;
						parent = parent.parentNode;
						if (count >= 2 || parent == null) {
							val = " ";
						} else {
							val = getContentMouseOver(parent).replace(
									/^\s+|\s+$/g, "");
						}
					}
				} else if (action == actionCapt.BROWSER_FORM_SUBMIT) {
					val = "ACTION=[" + target.getAttribute("action")
							+ "], METHOD=[" + target.getAttribute("method")
							+ "]";
				}

				return val ? val : "";
			}

			function getContentMouseOver(target) {
				if (target.getAttribute("title")) {
					return target.getAttribute("title");
				} else if (target.getAttribute("alt")) {
					return target.getAttribute("alt");
				} else if (target.value) {
					return target.value;
				} else if (target.innerHtml) {
					return target.innerHTML; /* TODO: change to text */
				}
				return "";
			}

			// Referente ao plugin da UseSkill Capture
			function filterAction(e, el, defaolt) {
				var isOnUseSkill = getParentsOn(el, document
						.querySelectorAll('.UseSkill-nocapt')), action = isOnUseSkill;

				if (isOnUseSkill
						&& (e.type == actionCapt.CLICK
								|| e.type == actionCapt.DBLCLICK
								|| e.type == actionCapt.TAP || e.type == actionCapt.DBLTAP)) {
					var id = el.id;
					var idParent = el.parentNode ? el.parentNode.id : null;
					action = isOnUseSkillDIV(isOnUseSkill, id, idParent);
				}

				if (action == true) {
					action = null;
				} else if (action == false) {
					action = defaolt;
				}
				return action;
			}

			function getParents(el) {
				var parents = [];
				var p = el.parentNode;
				while (p !== null) {
					var o = p;
					parents.push(o);
					p = o.parentNode;
				}
				return parents; // returns an Array []
			}

			function getParentsOn(el, arr) {
				var parents = getParents(el);
				for ( var i in arr) {
					if (parents.indexOf(arr[i]) != -1) {
						return true;
					}
				}
				return false;
			}

			function getTextByElement(elem) {
				return getText([ elem ]);
			}

			function getText(elems) {
				var ret = "", elem;

				for (var i = 0; elems[i]; i++) {
					elem = elems[i];

					// Get the text from text nodes and CDATA nodes
					if (elem.nodeType === 3 || elem.nodeType === 4) {
						ret += elem.nodeValue;

						// Traverse everything else, except comment nodes
					} else if (elem.nodeType !== 8) {
						ret += getText(elem.childNodes);
					}
				}

				return ret;
			}
			;

			function get_browser() {
				var ua = navigator.userAgent, tem, M = ua
						.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i)
						|| [];
				if (/trident/i.test(M[1])) {
					tem = /\brv[ :]+(\d+)/g.exec(ua) || [];
					return 'IE ' + (tem[1] || '');
				}
				if (M[1] === 'Chrome') {
					tem = ua.match(/\bOPR\/(\d+)/)
					if (tem != null) {
						return 'Opera ' + tem[1];
					}
				}
				M = M[2] ? [ M[1], M[2] ] : [ navigator.appName,
						navigator.appVersion, '-?' ];
				if ((tem = ua.match(/version\/(\d+)/i)) != null) {
					M.splice(1, 1, tem[1]);
				}
				return M[0];
			}

			function get_browser_version() {
				var ua = navigator.userAgent, tem, M = ua
						.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i)
						|| [];
				if (/trident/i.test(M[1])) {
					tem = /\brv[ :]+(\d+)/g.exec(ua) || [];
					return 'IE ' + (tem[1] || '');
				}
				if (M[1] === 'Chrome') {
					tem = ua.match(/\bOPR\/(\d+)/)
					if (tem != null) {
						return 'Opera ' + tem[1];
					}
				}
				M = M[2] ? [ M[1], M[2] ] : [ navigator.appName,
						navigator.appVersion, '-?' ];
				if ((tem = ua.match(/version\/(\d+)/i)) != null) {
					M.splice(1, 1, tem[1]);
				}
				return M[1];
			}

			/**
			 * Funcao para alterar a acao em elementos especificos da UseSkill
			 */
			function isOnUseSkillDIV(isOnUseSkill, id, idParent) {
				var action = false;
				if (isOnUseSkill) {
					action = true;
					if (id == "USroteiroFull" || idParent == "USroteiroFull") {
						action = actionCapt.ROTEIRO;
					} else if (id == "USconcluirTarefa"
							|| idParent == "USconcluirTarefa") {
						action = actionCapt.CONCLUIR;
					} else if (id == "UScomentarioEnviar"
							|| idParent == "UScomentarioEnviar") {
						action = actionCapt.COMENTARIO;
					} else if (id == "USpularTarefa"
							|| idParent == "USpularTarefa") {
						action = actionCapt.PULAR;
					}
				}
				return action;
			}

			function getAcoesString() {
				return localStorage.getItem(LOCALSTORAGE_VAR);
			}

			function getAcoes() {
				var str = localStorage.getItem(LOCALSTORAGE_VAR);
				return str != 'null' && str != null ? parseJSON(str) : [];
			}

			function printAcoes() {
				if (CONNECTED_PLUGIN) {
					chrome.extension.sendRequest({
						useskill : "getAcoes"
					}, function(dados) {
						debug("Actions Plugin: ", dados);
					});
				} else {
					debug("Actions: ", getAcoes());
				}
			}

			function addAcao(acao) {
				debug('addAcao', acao);
				console.log(acao);
				if (CONNECTED_PLUGIN) {
					var stringAcao = stringfyJSON(acao);
					debug("Add Action Plugin: ", acao);
					chrome.extension.sendRequest({
						useskill : "addAcao",
						acao : stringAcao
					});
				} else {
					var arr = getAcoes();
					if (Array.isArray(arr)) {
						// ORÁCULO
						if (arr.length) {
							var ultimaAcao = arr[arr.length - 1];
							ultimaAcao.sOracleUrl = acao.sUrl;
							if (ultimaAcao.sActionType === actionCapt.CLICK
									|| ultimaAcao.sActionType === actionCapt.DBLCLICK
									|| ultimaAcao.sActionType === actionCapt.BROWSER_FORM_SUBMIT
									|| acao.sActionType === actionCapt.BROWSER_ONLOAD) {

								ultimaAcao.sOracleVisibleElements = acao.sOracleVisibleElements;
								console
										.log(arr[arr.length - 1].sOracleVisibleElements);
							}
						}
						arr.push(acao);
					} else {
						arr = [ acao ];
					}
					var str = stringfyJSON(arr);
					debug("Add Action: ", acao);
					localStorage.setItem(LOCALSTORAGE_VAR, str);
				}
			}

			function setItem(name, acoes) {
				var x = stringfyJSON(acoes);
				localStorage.setItem(name, x);
			}

			function clearStorage() {
				localStorage.removeItem(LOCALSTORAGE_VAR);
			}

			function getItem(name) {
				var strObj = localStorage.getItem(name);
				return parseJSON(strObj);
			}

			function getUrl() {
				return location.protocol + "//" + location.host
						+ location.pathname;
			}

			function parseJSON(data) {
				return window.JSON && window.JSON.parse ? window.JSON
						.parse(data) : (new Function("return " + data))();
			}

			function stringfyJSON(data) {
				return window.JSON && window.JSON.stringify ? window.JSON
						.stringify(data) : (new Function("return " + data))();
			}

			function createXPathFromElement(elm) {
				var allNodes = document.getElementsByTagName('*');
				for (segs = []; elm && elm.nodeType == 1; elm = elm.parentNode) {
					if (elm.hasAttribute('id')) {
						var uniqueIdCount = 0;
						for (var n = 0; n < allNodes.length; n++) {
							if (allNodes[n].hasAttribute('id')
									&& allNodes[n].id == elm.id)
								uniqueIdCount++;
							if (uniqueIdCount > 1)
								break;
						}
						;
						if (uniqueIdCount == 1) {
							segs
									.unshift('id("' + elm.getAttribute('id')
											+ '")');
							return segs.join('/');
						} else {
							segs.unshift(elm.localName.toLowerCase() + '[@id="'
									+ elm.getAttribute('id') + '"]');
						}
					} else if (elm.hasAttribute('class')) {
						segs.unshift(elm.localName.toLowerCase() + '[@class="'
								+ elm.getAttribute('class') + '"]');
					} else {
						for (i = 1, sib = elm.previousSibling; sib; sib = sib.previousSibling) {
							if (sib.localName == elm.localName)
								i++;
						}
						;
						segs.unshift(elm.localName.toLowerCase() + '[' + i
								+ ']');
					}
					;
				}
				;
				return segs.length ? '/' + segs.join('/') : null;
			}

			function lookupElementByXPath(path) {
				var evaluator = new XPathEvaluator();
				var result = evaluator.evaluate(path, document.documentElement,
						null, XPathResult.FIRST_ORDERED_NODE_TYPE, null);
				return result.singleNodeValue;
			}

			// START ORACLE FUNCTIONS
			function getOracleElementsCount() {
				var countElements = 0;
				for ( var e in ORACLE_ELEMENTS) {
					var elem = ORACLE_ELEMENTS[e];
					if (elem) {
						countElements += document.querySelectorAll(elem).length;
					}
				}
				return countElements;
			}

			function getOracleVisibleElements() {
				return $(":visible").length;
			}

			// END ORACLE FUNCTIONS
		}

		// Start Capt
		if (DOM_READY) {
			document.addEventListener("DOMContentLoaded", function(event) {
				runCapt();
			});
		} else {
			runCapt();
		}
	}

	function useskill_capt_clearStorage() {
		localStorage.removeItem("useskill_actions");
		localStorage.removeItem("useskill_actions_plugin");
		window.useSkillActions = '[]';
	}
} catch (err) {
	console.log('Capt are sleeping :)', err);
}