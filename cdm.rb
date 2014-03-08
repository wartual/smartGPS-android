require 'gcm'

gcm = GCM.new('AIzaSyAZM05G9vmxsBScFmW42iI0geoGmdryG0g')
registration_ids= [" APA91bG6hUlUbzKkYbx8jyEwQOHyR3uEM6SlmSHuqE5y8OPzTuGxqqfKTJNkMkC6sAREPDcdFnR88bHTIT0Cr_qmAQjJeOFpvYHe5hM2Blbxfq0-KmYLmzU8qcT66UQxCgHovHqnB3pBbzOkQtHo3QOfeOm7kkoFiQ"] # an array of one or more client registration IDs

data = {
        nodes: [
            {
                type: "regular",
                latitude: 46.0573536,
                longitude: 14.5059252,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0573536,
                longitude: 14.5059252,
                id: 79443
            },
            {
                type: "regular",
                latitude: 46.0574551,
                longitude: 14.5060362,
                id: 79443
            },
            {
                type: "regular",
                latitude: 46.0575139,
                longitude: 14.5061565,
                id: 79443
            },
            {
                type: "regular",
                latitude: 46.0575715,
                longitude: 14.5063875,
                id: 79443
            },
            {
                type: "regular",
                latitude: 46.0575991,
                longitude: 14.5083016,
                id: 79443
            },
            {
                type: "regular",
                latitude: 46.0576025,
                longitude: 14.5085284,
                id: 79443
            },
            {
                type: "regular",
                latitude: 46.0564176,
                longitude: 14.5079953,
                id: 79443
            },
            {
                type: "regular",
                latitude: 46.0554046,
                longitude: 14.5075247,
                id: 79443
            },
            {
                type: "regular",
                latitude: 46.0556156,
                longitude: 14.5064111,
                id: 79443
            },
            {
                type: "regular",
                latitude: 46.0566424,
                longitude: 14.5068472,
                id: 79443
            },
            {
                type: "foursquare",
                latitude: 46.0568216,
                longitude: 14.5059567,
                id: 79443
            },
            {
                type: "regular",
                latitude: 46.0568216,
                longitude: 14.5059567,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0566424,
                longitude: 14.5068472,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0564176,
                longitude: 14.5079953,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0554046,
                longitude: 14.5075247,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0556156,
                longitude: 14.5064111,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0558774,
                longitude: 14.5051944,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0558997,
                longitude: 14.5051082,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0559763,
                longitude: 14.5049576,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0560241,
                longitude: 14.5047512,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0561384,
                longitude: 14.5046995,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0562116,
                longitude: 14.5043942,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0562338,
                longitude: 14.5042698,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0562627,
                longitude: 14.5041181,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0562777,
                longitude: 14.5040907,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0562912,
                longitude: 14.5040361,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0570279,
                longitude: 14.5043623,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.05711,
                longitude: 14.5044066,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0572654,
                longitude: 14.5031133,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0576072,
                longitude: 14.5032243,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0576773,
                longitude: 14.5034778,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.057738,
                longitude: 14.5038283,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0577662,
                longitude: 14.5041757,
                id: 97650
            },
            {
                type: "regular",
                latitude: 46.0577589,
                longitude: 14.5047591,
                id: 97650
            },
            {
                type: "foursquare",
                latitude: 46.0577221,
                longitude: 14.505087,
                id: 97650
            },
            {
                type: "foursquare",
                latitude: 46.0577221,
                longitude: 14.505087,
                id: 0
            },
            {
                type: "regular",
                latitude: 46.0577221,
                longitude: 14.505087,
                id: 28699
            },
            {
                type: "regular",
                latitude: 46.0576034,
                longitude: 14.5057889,
                id: 28699
            },
            {
                type: "regular",
                latitude: 46.0568894,
                longitude: 14.5055039,
                id: 28699
            },
            {
                type: "regular",
                latitude: 46.0560011,
                longitude: 14.5051516,
                id: 28699
            },
            {
                type: "regular",
                latitude: 46.0558997,
                longitude: 14.5051082,
                id: 28699
            },
            {
                type: "regular",
                latitude: 46.0558774,
                longitude: 14.5051944,
                id: 28699
            },
            {
                type: "regular",
                latitude: 46.0568628,
                longitude: 14.5056012,
                id: 28699
            },
            {
                type: "foursquare",
                latitude: 46.0571804,
                longitude: 14.505765,
                id: 28699
            },
            {
                type: "regular",
                latitude: 46.0571804,
                longitude: 14.505765,
                id: 2668
            },
            {
                type: "regular",
                latitude: 46.0573536,
                longitude: 14.5059252,
                id: 2668
            },
            {
                type: "regular",
                latitude: 46.0574551,
                longitude: 14.5060362,
                id: 2668
            },
            {
                type: "regular",
                latitude: 46.0574551,
                longitude: 14.5060362,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0575139,
                longitude: 14.5061565,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0575715,
                longitude: 14.5063875,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0575991,
                longitude: 14.5083016,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0576144,
                longitude: 14.5083923,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0576293,
                longitude: 14.5084332,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.057657,
                longitude: 14.5084491,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0577127,
                longitude: 14.5084362,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0577437,
                longitude: 14.5083736,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.057757,
                longitude: 14.5082612,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.057686,
                longitude: 14.507037,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0576834,
                longitude: 14.5066542,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0576806,
                longitude: 14.5063505,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0576946,
                longitude: 14.5059725,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0577104,
                longitude: 14.5058357,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0576034,
                longitude: 14.5057889,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0568894,
                longitude: 14.5055039,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0560011,
                longitude: 14.5051516,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0558997,
                longitude: 14.5051082,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0549756,
                longitude: 14.5046862,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.054545,
                longitude: 14.5044668,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0544267,
                longitude: 14.5043994,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0543597,
                longitude: 14.5044835,
                id: 59011
            },
            {
                type: "regular",
                latitude: 46.0545058,
                longitude: 14.5045549,
                id: 59011
            }
        ],
        travelData: {
            id: "null",
            userId: "4c1fe061-b0ba-424f-939a-045e0b25e8f0",
            departureAddress: "Slovenska cesta 20",
            departureLatitude: 46.05737474638258,
            departureLongitude: 14.50594425201416,
            destinationAddress: "Slovenska cesta 30",
            destinationLatitude: 46.05445617291736,
            destinationLongitude: 14.504635334014893,
            currentLatitude: 0,
            currentLongitude: 0,
            time: 0,
            distance: 0,
            statusId: "null",
            dateCreated: 0,
            dateUpdated: 0
        }
    }
aps = { alert: data, badge: 1, sound: "default"}


options = {data: aps, collapse_key: "updated_score"}
response = gcm.send_notification(registration_ids, options)
