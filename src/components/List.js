var React = require('react-native');
var Store = require('../Store');
var Actions = require('../Actions');
const  {
  MKCheckbox
} = require('react-native-material-kit') ;

var {
  StyleSheet,
  View,
  ListView,
  Text,
  TouchableHighlight
} = React;

var styles = StyleSheet.create({
  col: {
    flex: 1,
    flexDirection: 'column',
  },
	row: {
	    flexDirection: 'row',
	},
  legendLabel: {
    textAlign: 'center',
    color: '#666666',
    marginTop: 10, marginBottom: 20,
    fontSize: 12,
    fontWeight: '300',
  },
	deleteButton:{
	    flex:1,
	    borderWidth:1,
	    borderColor:'black',
	    borderRadius:5,
  	},
	listView: {
   		paddingLeft: 30,
   		paddingRight: 30
  	}
});

module.exports = class extends React.Component{

	constructor(props) {
	    super(props);
	    this.state = {
			dataSource: new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2})
	    };

	    this.listener = this._listener.bind(this);
	    this.renderRow = this._renderRow.bind(this);
	}

	_listener(){
		this.setState({
			dataSource: this.state.dataSource.cloneWithRows(Store.get())
	    });
	}

	_onDeletePress(rowId){
		Actions.delete(parseInt(rowId,10));
	}
	_onDonePress(rowId){
		Actions.toggleDone(parseInt(rowId,10));
	}


	componentDidMount() {
	    Store.addChangeListener(this.listener);
	}

	componentWillUnmount() {
		Store.removeChangeListener(this.listener);
	}

	_renderRow(todo,sectionID,rowId) {
		return (
      <View style={styles.row}>
        <MKCheckbox checked={todo.done} onCheckedChange={this._onDonePress.bind(this,rowId)} />
        <Text style={styles.legendLabel}>{todo.text}</Text>
      </View>
	    );
	}

	render() {
	    return (
	        <View>
	        	<ListView
	        		style={styles.listView}
	        		automaticallyAdjustContentInsets={false}
  					dataSource={this.state.dataSource}
          			renderRow={this.renderRow}/>
	        </View>
	    );
	}
};
