import { combineReducers } from 'redux';
import { persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';

import postobjReducer from "./postobjReducer";
const persistConfig = {
  key: "root",
  storage,
  whitelist: ["postobjReducer"]
};

const rootReducer = combineReducers({
    postobjReducer,
});

export default persistReducer(persistConfig,rootReducer);
