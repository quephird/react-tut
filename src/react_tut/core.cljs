(ns ^:figwheel-always react-tut.core
    (:require[om.core :as om :include-macros true]
              [om.dom :as dom :include-macros true]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(def app-state
  (atom
    {:comments
      [{:author "danielle" :text "<p>This is a comment</p>"}
       {:author "kurtis" :text "<p>This is <em>another</em> comment</p>"}]}))

(defn comment-view [data owner]
  (reify
    om/IRender
    (render [_]
      (dom/div #js {:className "comment"}
        (dom/h2 #js {:className "commentAuthor"}
          (dom/span nil (:author data)))
        (dom/span #js {:dangerouslySetInnerHTML #js {:__html (:text data)}} nil)))))

(defn comment-list-view [data owner]
  (reify
    om/IRender
    (render [_]
      (apply dom/div #js {:className "commentList"}
        (om/build-all comment-view (:comments data))))))

(defn comment-form-view [data owner]
  (reify
    om/IRender
    (render [_]
      (dom/div #js {:className "commentForm"}
               "Hello, world! I am a CommentForm."))))

(defn comment-box-view [data owner]
  (reify
    om/IRender
      (render [_]
        (dom/div #js {:className "commentBox"}
          (dom/h1 nil "Comments")
          (om/build comment-list-view data)
          (om/build comment-form-view data)))))

(om/root comment-box-view app-state
  {:target (. js/document (getElementById "content"))})

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
