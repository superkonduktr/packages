(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.1" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "1.2.3")
(def +version+ (str +lib-version+ "-0"))

(task-options!
  pom {:project     'cljsjs/ace
       :version     +version+
       :description "Ajax.org Cloud9 Editor"
       :url         "https://ace.c9.io"
       :license     {"BSD" "https://opensource.org/licenses/BSD-3-Clause"}
       :scm         {:url "https://github.com/cljsjs/packages"}})

(deftask package []
  (comp
   (download :url (str "https://github.com/ajaxorg/ace-builds/archive/v" +lib-version+ ".zip")
             :checksum "49C4A68662EA7AE09E4359C90E83A4C3"
             :unzip true)
   (sift :move {#"^ace-builds-([\d\.]*)/src-noconflict/ace.js" "cljsjs/ace/development/ace.inc.js"
                #"^ace-builds-([\d\.]*)/src-min-noconflict/ace.js" "cljsjs/ace/production/ace.min.inc.js"})
   (sift :include #{#"^cljsjs" #"^deps\.cljs$"})
   (pom)
   (jar)))
