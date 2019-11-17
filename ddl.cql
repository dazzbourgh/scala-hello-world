create keyspace vk with replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};

create table vk.event (event_url text primary key, text text, action text, tags list<text>);
CREATE CUSTOM INDEX text_idx ON vk.event (text) USING 'org.apache.cassandra.index.sasi.SASIIndex' WITH OPTIONS = {'mode': 'CONTAINS', 'analyzer_class': 'org.apache.cassandra.index.sasi.analyzer.StandardAnalyzer', 'case_sensitive': 'false'};
insert into vk.event (event_url, text, action, tags) values ('https://text.test', 'some test event', 'new', ['test']);
