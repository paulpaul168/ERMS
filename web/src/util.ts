// utils.js

import React, { useState, useEffect, useRef } from "react";

export const useInterval = (callback: () => void, delay: number) => {
  const savedCallback = useRef(null as unknown as () => void);

  useEffect(() => {
    savedCallback.current = callback;
  }, [callback]);

  useEffect(() => {
    function tick() {
      savedCallback.current();
    }
    tick();
    if (delay !== null) {
      const id = setInterval(tick, delay);
      return () => clearInterval(id);
    }
  }, [delay]);
};
