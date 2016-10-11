clear
%Get files, excluding . and ..
files = dir('../../results');
files = files(3:end);

for file=files';
   data = csvread(strcat('../../results/',file.name));
   %Extract the interesting parts of the filename
   expression = 'A-(?<algorithm>[^-_]*)_N-(?<numAgents\>\d*)_S-(?<width>\d*)x(?<hight>\d*)_E-(?<easy>[\d\.]*)\.csv';
   meta = regexp(file.name,expression,'names');
   
   plot(data(:,1), data(:,3));
end