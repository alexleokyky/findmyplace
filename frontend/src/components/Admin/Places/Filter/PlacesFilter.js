import React, {Component} from 'react';
import {Button, Col, Input, Row} from "react-materialize";

class PlacesFilter extends Component {

    constructor(props) {
        super(props);

        this.state = {
            places: this.props.places,
            placeTypes: [{"name":"Hotel"}, {"name":"Parking"}, {"name":"Cafe"}],
            selectedPlaceType: "None",
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleFilter = this.handleFilter.bind(this);
    }

    componentDidMount() {
        fetch("/places/types")
            .then((response) => response.json())
            .then((result) => {
                    this.setState({
                        placeTypes: result
                    });
                }
            )
    }

    handleChange(key, value) {
        this.setState({[key]: value});
    }

    handleFilter() {
        let places = this.state.places.filter((place) => {
            if ((this.state.selectedPlaceType === "None")) {
                return place;
            } else {
                return place.placeType.name === this.state.selectedPlaceType;
            }
        });

        this.props.handleUpdate(places);
    }

    render() {
        const places = this.state.placeTypes;

        return (
            <Row className="filter-wrapper">
                <Col s={3} className="filter-places">
                    <Input  type='select'
                            label="Place Type"
                            onChange={e => this.handleChange("selectedPlaceType", e.target.value)}>
                            <option key="None" value="None">None</option>
                            {
                                places.map((place) => (
                                    <option key={place.name} value={place.name}>{place.name}</option>
                                ))
                            }
                    </Input>
                </Col>
                <Button onClick={this.handleFilter}>Filter</Button>
            </Row>
        );
    }

}

export default PlacesFilter;