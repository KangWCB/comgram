let init = {
    postobj: [],
};

const postobjReducer = (state = init, action) => {
    switch (action.type)
    {
        case "OBJECT_ADD": 
            console.log("objadd")
 
            return {...state, postobj: action.payload}
            
        case "OBJECT_UPDATE":
            console.log("objupdate")
            let idx = state.postobj.findIndex(function(data){ return data.id === action.payload['id']})
            const arr = [...state.postobj];
            console.log(arr[idx])
            arr[idx] = action.payload;
            console.log(arr[idx])
            return {...state, postobj: arr};

        case "OBJECT_RESET":
            console.log("reset");
            return init;
        
        default:
            return state;
    };

};

export default postobjReducer;