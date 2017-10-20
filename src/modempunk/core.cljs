(ns modempunk.core
  (:require [reagent.core :as r]
            [thi.ng.geom.core :as g]
            [thi.ng.geom.core.matrix :as mat :refer [M32 M44]]
            [thi.ng.geom.webgl.animator :refer [animate]]
            [thi.ng.geom.svg.core :as svg]
            [goog.dom]
            [goog.math]))

(def by-id goog.dom.getElement)

(def state (r/atom {:ship {:position [100 100]
                           :heading 0}}))

(defn mouse-event->position [e]
  (let [raw-x (.-clientX e)
        raw-y (.-clientY e)
        bounding-rect (-> (.-target e)
                          (.getBoundingClientRect))
        left (-> bounding-rect
                 (.-left))
        top (-> bounding-rect
                (.-left))
        x (- raw-x left)
        y (- raw-y top)]
    [x y]))


(defn ship-angle [[x1 y1] [x2 y2]]
  (->> (goog.math.angle x1 y1 x2 y2)
       (+ 90)                          ; 90° offset in ship coordinate
                                       ; system
       goog.math.standardAngle))


(defn background [ship-state]
  (svg/group
   {:width "100%"
    :height "100%"
    :class "blackout-fill"
    :on-click (fn [e]
                (->> e
                     (mouse-event->position)
                     (ship-angle (-> ship-state
                                    :position))
                     (swap! state assoc-in [:ship :heading])))}
   (svg/rect [0 0] "100%" "100%")))

(defn svg-rotation
  "Returns a string like rotate(45, 100, 100) to rotate 45 degrees
  around the point 100, 100"
  [{:keys [heading]
    [x y] :position
    :as ship-state}]
  (str "rotate(" heading ", " x ", " y ")"))

(defn ship [{:keys [position heading]
             [x y] :position
             :as ship-state}]
  (let [ship-radius 10
        heading-pos [x
                     (- y
                        (* 2 ship-radius))]]
    (svg/group
     {:stroke-width 0.5
      :fill "none"
      :class "pank-stroke"
      :transform (svg-rotation ship-state)}
     (svg/circle position ship-radius)
     (svg/group
      {:class "yello-stroke"}
      (svg/line position heading-pos)))))

(defn app-container []
  (let [ship-state (:ship @state)]
    (svg/svg
     {:width 600
      :height 600}
     (svg/group
      {:key "g1"}
      (background ship-state)
      (ship ship-state)))))

(r/render-component [app-container] (by-id "app"))
