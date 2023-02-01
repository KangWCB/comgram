let init = {
    postobj: [],
};

const postobjReducer = (state = init, action) => {
    switch (action.type)
    {
        case "OBJECT_ADD": 
 
            return {...state, postobj: action.payload}
            
        case "OBJECT_UPDATE":
            let idx = state.postobj.findIndex(function(data){ return data.id === action.payload['id']})
            const arr = [...state.postobj];
            arr[idx] = action.payload;
            return {...state, postobj: arr};

        case "OBJECT_RESET":
            return init;
        
        default:
            return state;
    };

};

export default postobjReducer;