const init = {
    postobj: [],
};

const postobjReducer = (state = init, action) => {
    switch (action.type)
    {
        case "OBJECT_ADD": 
            console.log("objadd")
            console.log(action.payload);
            console.log(action.payload[0]['id']);
            console.log(state);
            /*
            let existCheck = false;
            for(var i in state.postobj)
            {
                console.log("hi");
                if(state.postobj[i].id == action.payload[i]['id']) existCheck = true;
            };
            if (existCheck)
            {   
                return state;
            }
            else // {...state, postobj : state.postobj?.concat(action.payload)}
            */    return {...state, postobj : action.payload}
            
        case "OBJECT_UPDATE":
            console.log("objupdate")
            console.log(state)
            let idx = state.postobj.findIndex(function(data){ return data.id === action.payload['id']})
            console.log(idx)
            return state.postobj[idx] = action.payload;

        case "OBJECT_RESET":
            console.log("reset");
            return init;
        
        default:
            return state;
    };

};

export default postobjReducer;